package com.zte.scope.ems;

import com.zte.concept.AbstractDomain;
import com.zte.concept.IDomain;
import com.zte.concept.IMoOperation;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.exception.AckException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NoTargetException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.AckResult;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.service.MOS;
import com.zte.mos.service.cmd.MoCommandExecutor;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.mos.util.msg.MoFindMsg;
import com.zte.persist.BundleToEmsBind;
import com.zte.persist.EmsDB;
import com.zte.scope.bundle.BundleDomain;
import com.zte.scope.bundle.BundleMIB;
import com.zte.scope.common.AckPool;

import java.util.*;

import static com.zte.domain.transaction.TransactionMonitorService.startTransactionMonitorService;
import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-7-14.
 */
public class EmsDomain extends AbstractDomain
{
    private static Logger log = logger(EmsDomain.class);
    private EmsMIB mib = EmsMIB.instance;
    private HashMap<String, BundleDomain> children = new HashMap<String, BundleDomain>();

    public EmsDomain(String emsId, MOS mos) throws MOSException
    {
        super(emsId, mos);
        this.operationPool = EmsOperationPool.pool;
        initBundle();
        startTransactionMonitorService();
    }

    private void initBundle() throws MOSException
    {
        List<BundleToEmsBind> bundleList = EmsDB.getService().getAll();
        for (BundleToEmsBind bundle : bundleList)
        {
            BundleDomain domain = new BundleDomain(bundle.bundleId, this.mos);
            children.put(domain.getID(), domain);
        }
    }

    public BundleDomain createBundle(String bundleId, String bundleUrl) throws MOSException
    {
        log.debug("start to init bundle address, id " + bundleId + " url " + bundleUrl);
        BundleDomain bundle = new BundleDomain(bundleId, this.mos);
        bundle.setAddress(bundleUrl);
        EmsDB.getService().put(new BundleToEmsBind(bundleId));
        children.put(bundle.getID(), bundle);
        return bundle;
    }

    public void updateBundle(String bundleId, String bundleUrl) throws MOSException
    {
        if (children.containsKey(bundleId))
        {
            log.debug("start to update bundle address, id " + bundleId + " url " + bundleUrl);
            children.get(bundleId).setAddress(bundleUrl);
        }
        else
        {
            throw new MOSException("invalid bundle!");
        }
    }

    @Override
    public boolean localSupported(DN dn)
    {
        return /*!dnObj.isSizeInOdd() && */mib.contains(new DNWrapper(dn.toString()).evenPos());
    }

    @Override
    public boolean localOperable(DN dn, MoCmds cmd)
    {
        return EmsOperationPool.pool.isRegistered(dn, cmd);
    }

    @Override
    public FindResult broadcast(MoFindMsg msg)
    {
        FindResult result = new FindResult(msg.getTransactionID());
        for (IDomain domain : children.values())
        {
            FindResult onRes = domain.broadcast(msg);
            result.merge(onRes);
        }
        return result;
    }

    private BundleDomain getDestBundle(MoMsg msg) throws MOSException
    {
        BundleDomain bundle;
        String dn = msg.dn();
        DN dnObj = new DN(dn);
//        if (msg.getCmd() == MoCmds.MoCreateRequest && dnObj.toMibID().equals("/Ems/Ne")){
//            bundle = this.pickBundle();
//        }else{
        bundle = EmsRoutingTable.service.getDestBundle(dnObj);
//        }
        return bundle;
    }

    @Override
    public Result forward(MoMsg msg) throws MOSException
    {
        BundleDomain bundle = this.getDestBundle(msg);
        if (bundle == null)
        {
            log.info("can not find ne-bundle :" + msg.toString());
            throw new NoTargetException("ne-bundle not exist:" + msg.dn());
        }
        return bundle.onMsg(msg);
    }

    public BundleDomain pickBundle() throws MOSException
    {
        for (BundleDomain bundle : children.values())
        {
            String bundleAddress = bundle.getAddress();
            if (bundleAddress == null || bundleAddress.isEmpty())
            {
                log.debug("skip invalid bundle address");
                continue;
            }
            if (bundle.canManageMore())
            {
                return bundle;
            }
        }
        throw new MOSException("has no capacity");
    }

    public void allocateTrasnID(MoMsg msg)
    {
        if (msg instanceof MoConfigMsg && msg.getTransactionID().nothing())
        {
            msg.setTransId(new Maybe<Integer>(mos.generateTransId()));
        }
    }

    public BundleDomain getBundle(String id)
    {
        return this.children.get(id);
    }

    private List<String> getDNs(HashMap<BundleDomain, List<String>> bundles, BundleDomain bundle)
    {
        List<String> dnList = bundles.get(bundle);
        List<String> list;
        if (dnList == null)
        {
            list = new ArrayList<String>();
            bundles.put(bundle, list);
        }
        else
        {
            list = bundles.get(bundle);
        }
        return list;
    }



    public boolean parseAckMsg(MoAckMsg ack,
                            Queue<Pair<BundleDomain, String>> neAckList,
                            HashMap<BundleDomain, List<String>> forwardMap)
    {
        String[] dns = ack.dns();
        boolean needAckLocal = false;

        for(String dn : dns)
        {
            String mib = new DNWrapper(dn).evenPos();
            boolean isLocalObj =
                    EmsMIB.instance.contains(mib)
                            || BundleMIB.instance.contains(mib);

            if (!needAckLocal)
            {
                needAckLocal = isLocalObj;
            }
            try
            {
                if (!isLocalObj || mib.equals("/Ems/Ne") || mib.equals("/Ems/Ne/Product"))
                {
                    BundleDomain bundle = EmsRoutingTable.service.getDestBundle(new DN(dn));
                    List<String> dnsInBundle = getDNs(forwardMap, bundle);
                    dnsInBundle.add(dn);
                    if (mib.equals("/Ems/Ne"))
                    {
                        neAckList.add(new Pair<BundleDomain, String>(bundle, dn));
                    }
                }
            }
            catch (Exception e)
            {
                logger(EmsDomain.class).error(" fail to parse ack in ems domain ", e);
            }
        }

        return needAckLocal;

    }

    private AckResult doLocalAck(MoAckMsg ack)
    {
        AckResult result = new AckResult(ack.getTransactionID());
        try
        {
            result = (AckResult) MoCommandExecutor.execute(ack, mos);
        }
        catch (MOSException e)
        {
            result = result.push(new AckException(e, ack.dns()));
        }
        return result;

    }

    private AckResult doRemoteAck(MoAckMsg ack, HashMap<BundleDomain, List<String>> forwardMap)
    {
        AckResult result = new AckResult(ack.getTransactionID());

        for (BundleDomain bundle : forwardMap.keySet())
        {
            MoAckMsg bundleAck = ack.clone();
            List<String> dnsList = forwardMap.get(bundle);
            String[] dnsOfBundle = dnsList.toArray(new String[dnsList.size()]);
            bundleAck.setDn(dnsOfBundle);
            try
            {
                if (bundle != null)
                {
                    bundle.forward(bundleAck);
                }
                else
                {
                    logger(EmsDomain.class).error("fail to forward  msg to bundle , msg " + bundleAck);
                }
            }
            catch (MOSException e)
            {
                result.push(new AckException(e, bundleAck.dns()));
            }
        }

        return result;

    }


    public Result onAck(MoAckMsg ack)
    {
        HashMap<BundleDomain, List<String>> forwardMap = new HashMap<BundleDomain, List<String>>();
        Queue<Pair<BundleDomain, String>> neAckList = new LinkedList<Pair<BundleDomain, String>>();
        Boolean needAckLocal = parseAckMsg(ack, neAckList, forwardMap);
        AckResult result = new AckResult(ack.getTransactionID());

        for(Pair<BundleDomain, String> pair : neAckList)
        {
            IMoOperation oper = AckPool.getOperation(new DNWrapper(pair.second()).evenPos());
            try
            {
                oper.ack(ack, pair.first());
            }
            catch (Exception e)
            {
                logger(EmsDomain.class).error(" fail to do ack in ems domain ", e);
            }
        }

        if (needAckLocal)
        {
            result = doLocalAck(ack);
        }
        result.push(doRemoteAck(ack, forwardMap));
        return result;


    }


//    public Result onAck(MoAckMsg ack)
//    {
//        String[] dns = ack.dns();
//        boolean isAckLocally = false;
//
//        HashMap<BundleDomain, List<String>> bundles = new HashMap<BundleDomain, List<String>>();
//        AckResult result = new AckResult(ack.getTransactionID());
//        for (String dn : dns)
//        {
//            String mib = new DNWrapper(dn).evenPos();
//            boolean isLocalObj =
//                    EmsMIB.instance.contains(mib)
//                            || BundleMIB.instance.contains(mib);
//
//            if (!isAckLocally)
//            {
//                isAckLocally = isLocalObj;
//            }
//
//            try
//            {
//                if (!isLocalObj || mib.equals("/Ems/Ne") || mib.equals("/Ems/Ne/Product"))
//                {
//                    BundleDomain bundle = EmsRoutingTable.service.getDestBundle(new DN(dn));
//                    List<String> dnsInBundle = getDNs(bundles, bundle);
//                    dnsInBundle.add(dn);
//                    if (mib.equals("/Ems/Ne"))
//                    {
//                        IMoOperation oper = AckPool.getOperation(mib);
//                        oper.ack(ack, bundle);
//                    }
//                }
//            }
//            catch (Exception e)
//            {
//                logger(EmsDomain.class).error(" fail to do ack in ems domain ", e);
//            }
//        }
//
//        try
//        {
//            if (isAckLocally)
//            {
//                result = (AckResult) MoCommandExecutor.execute(ack, mos);
//            }
//        }
//        catch (MOSException e)
//        {
//            result = result.push(new AckException(e, ack.dns()));
//        }
//
//        for (BundleDomain bundle : bundles.keySet())
//        {
//            MoAckMsg bundleAck = ack.clone();
//            List<String> dnsList = bundles.get(bundle);
//            String[] dnsOfBundle = dnsList.toArray(new String[dnsList.size()]);
//            bundleAck.setDn(dnsOfBundle);
//            try
//            {
//                if (bundle != null)
//                {
//                    bundle.forward(bundleAck);
//                }
//                else
//                {
//                    logger(EmsDomain.class).error("fail to forward  msg to bundle , msg " + bundleAck);
//                }
//            }
//            catch (MOSException e)
//            {
//                result.push(new AckException(e, bundleAck.dns()));
//            }
//        }
//        return result;
//    }
}
