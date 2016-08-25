package com.zte.mos.container;

import com.services.AppService;
import com.services.IPTarget;
import com.services.LocalNeService;
import com.zte.concept.AbstractDomain;
import com.zte.concept.DomainState;
import com.zte.concept.IDomain;
import com.zte.domain.transaction.TransactionContext;
import com.zte.domain.transaction.Transactional;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.AckException;
import com.zte.mos.exception.BatchException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.*;
import com.zte.mos.service.NoSuchTransException;
import com.zte.mos.service.Regable;
import com.zte.mos.storage.DataUnit;
import com.zte.mos.storage.MapDbService;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoFindMsg;

import java.util.*;

import static com.zte.concept.DomainState.Deleted;
import static com.zte.concept.DomainState.Empty;
import static com.zte.domain.transaction.TransactionMonitorService.endMonitorTransaction;
import static com.zte.domain.transaction.TransactionMonitorService.startMonitorTransaction;
import static com.zte.mos.storage.MapDbService.DbNameEnum.BUNDLE;
import static com.zte.mos.storage.MapDbService.DbNameEnum.NE_DOMAIN;
import static com.zte.mos.util.basic.Logger.logger;


public class RealBundleDomain extends AbstractDomain implements Regable, LocalNeService, Transactional
{
    private static Logger log = logger(RealBundleDomain.class);
    private HashMap<String, NeDomain> children = new HashMap<String, NeDomain>();
    private static final RealBundleDomain instance = new RealBundleDomain();


    @Override
    public Collection<? extends IPTarget> getIpTargets()
    {
        List<NeDomain> list = new ArrayList<NeDomain>();

        for(NeDomain domain : children.values())
        {
            if (domain.getState() == DomainState.Committed)
            {
                list.add(domain);
            }
            else
            {
                log.error("invalid domain state , neId :" + domain.getID() + " state: " + domain.getState());
            }
        }
        return list;
    }

    public void save(NeDomain domain)
    {
        this.children.put(domain.getID(), domain);
    }

    public void remove(NeDomain domain)
    {
        NeDomain old = this.children.get(domain.getID());
        if (old == domain) // same object
        {
            this.children.remove(domain.getID());
        }
    }

    public static RealBundleDomain getInstance()
    {
        return instance;
    }

    private RealBundleDomain()
    {
        super(null, null);
        log.debug("start to create real bundle domain " + this);
        try
        {
            init();
        }
        catch (Exception e)
        {
            log.error("init bundle error:", e);
        }
    }

    public Collection<NeDomain> getChildren()
    {
        return children.values();
    }

    private void init() throws Exception
    {
        DataUnit bundle = MapDbService.getDB(BUNDLE).get("BUNDLE");
        if (bundle != null)
        {
            String id = bundle.getData().get("id");
            log.debug("start to init bundle id :" + id);
            setID(id);
        }

        List<DataUnit> unitList = MapDbService.getDB(NE_DOMAIN).getAll();

        for (DataUnit unit : unitList)
        {
            try
            {
                NeIdentity ne = buildNeIdentity(unit);
                NeDomain neDomain = NeDomain.recover(ne);
                this.children.put(neDomain.getID(), neDomain);
            }
            catch (Throwable e)
            {
                logger(RealBundleDomain.class).error("fail to recover ne " + String.valueOf(unit.getData().get("ip")), e);
            }
        }
        AppService.publish(this);
    }

    private NeIdentity buildNeIdentity(DataUnit unit)
    {
        return new NeIdentity(unit.getPrimaryKey(), unit.getData().get("ip"), unit.getData().get("netype"));
    }

    public NeDomain getChild(String neId)
    {
        NeDomain neDomain = this.children.get(neId);
        if (neDomain != null && neDomain.getState() != Empty)
        {
            return neDomain;
        }
        else
        {
            return null;
        }
    }

    public NeDomain getChild(String neId, int transId)
    {
        NeDomain ne = this.children.get(neId);
        if (ne != null)
        {
            if (ne.isLockedBy(transId) &&
                    ne.getState() != Empty
                    && ne.getState() != Deleted)
            {
                return ne;
            }
            else
            {
                ne = null;
            }
        }
        return ne;
    }

    public void setID(String id) throws MOSException
    {
        log.debug("start to set bundle id: " + id);
        this.ID = id;
        if (ID != null)
        {
            DataUnit unit = new DataUnit("BUNDLE");
            unit.put("id", ID);
            MapDbService.getDB(BUNDLE).put(unit);
            doBdbTest(this.ID);
        }
    }

    private void doBdbTest(String expected) throws MOSException
    {
        DataUnit unit = MapDbService.getDB(BUNDLE).get("BUNDLE");

        if (unit == null)
        {
            log.debug("do bdb test failed, unit null");
            return;
        }

        if (!unit.getData().get("id").equalsIgnoreCase(expected))
        {
            log.debug("do bdb test failed, id " + unit.getData().get("id") + " expected " + expected);
            return;
        }

        log.debug("do bdb test passed");

    }

    public Result onAck(MoAckMsg ack) throws MOSException
    {

        String[] dns = ack.dns();
        HashSet<String> neSet = new HashSet<String>();
        HashSet<String> neDomainSet = new HashSet<String>();
        for (String dn : dns)
        {
            DNWrapper wrapper;
            String mib = (wrapper = new DNWrapper(dn)).evenPos();
            if (mib.equals("/Ems/Ne"))
            {
                neSet.add(dn + "/Product/1");
            }
            else if (mib.equals("/Ems/Ne/Product"))
            {
                neSet.add(dn);
            }
            else
            {
                neDomainSet.add(wrapper.to("Product"));
            }
        }
        AckResult result = new AckResult();
        for (String dn : neSet)
        {
            try
            {
                ack(ack, dn);
            }
            catch (MOSException e)
            {
                result.push(new AckException(e, dn));
            }
        }
        for (String neDomainDN : neDomainSet)
        {
            NeDomain neDomain = children.get(neDomainDN);
            try
            {
                if (neDomain != null)
                {
                    AckResult domainResult = (AckResult) neDomain.ack(ack);
                    if (!domainResult.isSuccess())
                    {
                        result.push(domainResult);
                    }
                }
                else
                {
                    log.error("fail to find ne domain for dn " + neDomainDN);
                }
            }
            catch (MOSException e)
            {
                log.error(neDomainDN + " fail to do ack commit", e);
                result.push(new AckException(e, neDomainDN));
            }
        }
        return result;
    }

    @Override
    protected Result dealAckInLocal(MoAckMsg ack)
    {
        return null;
    }

    @Override
    public Result forward(MoMsg msg)
    {
        if (!msg.getCmd().equals(MoCmds.MoAck))
        {
            String dn = msg.dn();
            DN dnObj = new DN(dn);
            try
            {
                String neDomainDN = dnObj.getHead("Product");
                NeDomain neDomain = children.get(neDomainDN);
                return neDomain.onMsg(msg);
            }
            catch (Exception e)
            {
                log.error("fail to forward nedomain none ack msg " + msg, e);
                return new Failure(e, msg.getTransactionID());
            }
        }
        else
        {
            HashSet<String> neSet = new HashSet<String>();
            MoAckMsg ack = (MoAckMsg) msg;
            String[] dns = ack.dns();
            for (String dn : dns)
            {
                DN dnObj = new DN(dn);
                try
                {
                    neSet.add(dnObj.getHead("Product"));
                }
                catch (NonvalidKeywordException e)
                {
                    log.error("fail to add to domain set, dn: " + dnObj, e);
                }
            }

            for (String neDomainDN : neSet)
            {
                NeDomain neDomain = children.get(neDomainDN);
                try
                {
                    neDomain.ack(ack);
                }
                catch (MOSException e)
                {
                    log.error("fail to forward nedomain ack msg, ne dn: " + neDomainDN + " ack msg:" + ack, e);
                }
            }
            return new Successful(msg.getTransactionID());
        }
    }

    @Override
    public boolean localSupported(DN dn)
    {
        return false;
    }

    @Override
    public boolean localOperable(DN dn, MoCmds cmd)
    {
        return false;
    }

    @Override
    public FindResult broadcast(MoFindMsg msg)
    {
        DN dn = new DN(msg.dn());
        String dnHead;
        FindResult result = new FindResult(msg.getTransactionID());
        try
        {
            dnHead = dn.getHead("Product");
            NeDomain ne = children.get(dnHead);
            if (ne != null)
            {
                result.merge(ne.broadcast(msg));
            }
        }
        catch (NonvalidKeywordException e)
        {
            for (IDomain domain : children.values())
            {
                FindResult onRes = domain.broadcast(msg);
                result.merge(onRes);
            }
        }

        return result;
    }

    void createNeDomain(NeIdentity identity, int transId) throws MOSException
    {
        if (this.children.containsKey(identity.dn))
        {
            log.debug(identity.dn + " fail to create ne domain, dn conflicts");
            throw new MOSException("dn conflicts:" + identity.dn);
        }
        log.debug("start to create ne domain " + identity.toString());
        NeDomain neDomain = NeDomain.newInstance(identity);
        logger(RealBundleDomain.class).debug("start start lock in createNeDomain, dn " + identity.dn + " transId " + transId);
        neDomain.lock(transId);
        startMonitorTransaction(this, neDomain.getID(), transId);
        this.children.put(neDomain.getID(), neDomain);
        logger(RealBundleDomain.class).debug("end start lock in createNeDomain, dn " + identity.dn + " transId " + transId);

    }

    void deleteNeDomain(String dn, int transId) throws MOSException
    {
        NeDomain ne = this.children.get(dn);
        if (ne == null)
        {
            log.debug(dn + " fail to delete ne domain, no such ne, transId " + transId);
            return;
        }
        logger(RealBundleDomain.class).debug("start start lock in deleteNeDomain, dn " + dn + " transId " + transId);
        ne.lock(transId);
        startMonitorTransaction(this, ne.getID(), transId);
        ne.delete();
        logger(RealBundleDomain.class).debug("end start lock in deleteNeDomain, dn " + dn + " transId " + transId);

    }

    void updateNeIP(String dn, String ip, int transId) throws MOSException
    {
        NeDomain ne = this.children.get(dn);
        if (ne == null)
        {
            log.debug(dn + " fail to update ne ip, no such ne, transId " + transId);
            throw new MOSException("no such NE : " + dn);
        }
        ne.updateIP(ip, transId);
        startMonitorTransaction(this, ne.getID(), transId);
    }


    private void ack(MoAckMsg ack, String neId) throws MOSException
    {
        int transId = ack.getTransactionID().getValue();
        logger(RealBundleDomain.class).debug(neId + " recv ack msg " + ack.dn() + "acktype " + ack.getAck().name() + " in transId " + transId);

        if (ack.getAck() == MoAckMsg.Ack.commit)
        {
            commit(transId, neId);
        }
        else
        {
            rollback(transId, neId);
        }
    }

    private void commit(int transId, String neId) throws MOSException
    {
        NeDomain ne = this.children.get(neId);
        if (ne == null)
        {
            log.debug(neId + " fail to do commit, no such ne , transId " + transId);
            throw new MOSException("no such NE : " + neId);
        }
        if (!ne.isLockedBy(transId))
        {
            logger(RealBundleDomain.class).error(neId + " fail to  commit , current transId " + transId);
            throw new NoSuchTransException("DN:" + neId + ", transaction id:" + transId);
        }
        endMonitorTransaction(this, ne.getID(), transId);
        ne.commit(transId);
        if (ne.getState() == Empty)
        {
            this.children.remove(neId);
        }
    }

    private void rollback(int transId, String neId) throws MOSException
    {
        NeDomain ne = this.children.get(neId);
        if (ne == null)
        {
            log.debug(neId + " fail to do rollback, no such ne , transId " + transId);
            throw new MOSException("no such NE : " + neId);
        }
        if (!ne.isLockedBy(transId))
        {
            logger(RealBundleDomain.class).error(neId + " fail to  rollback , current transId " + transId);
            throw new NoSuchTransException("DN:" + neId + ", transaction id:" + transId);
        }
        endMonitorTransaction(this, ne.getID(), transId);
        ne.rollback(transId);
        if (ne.getState() == Empty)
        {
            this.children.remove(neId);
        }
    }

    void commit(int transId, String[] neIds) throws MOSException
    {
        Set<String> set = new HashSet<String>();
        for (String neId : neIds)
        {
            set.add(neId);
        }
        for (String neId : set)
        {
            commit(transId, neId);
        }
        //ne.unlock(transId);
    }

    @Override
    public void onTimeOut(TransactionContext context) throws MOSException
    {
        rollback(context.getTransactionId(), context.getTargeId());
    }
}
