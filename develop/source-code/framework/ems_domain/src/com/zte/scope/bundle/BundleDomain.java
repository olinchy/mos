package com.zte.scope.bundle;

import com.odb.database.SimpleDB;
import com.zte.concept.AbstractDomain;
import com.zte.concept.IMoOperation;
import com.zte.domain.transaction.TransactionContext;
import com.zte.domain.transaction.Transactional;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.FindResult;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.service.BundleService;
import com.zte.mos.service.MOS;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.MoAckMsg;
import com.zte.mos.util.msg.MoFindMsg;
import com.zte.scope.ems.EmsRoutingTable;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by luoqingkai on 15-7-15.
 */
public class BundleDomain extends AbstractDomain implements Transactional
{
    private static Logger log = logger(BundleDomain.class);
    private static final int CAPACITY = 100000;
    private SimpleDB<String, NeToBundleBind> neDb;
    private String supportedVersion;
    private String address;
    private int neNum = 0;
    private NeTransaction neTransaction;
    private BundleService service;

    public BundleDomain(String bundleId, MOS mos)
    {
        super(bundleId, mos);
        this.operationPool = new BundleOperationPool();
        neDb = BundleDBFactory.newService(bundleId);
        this.neTransaction = new NeTransaction(neDb, this);
        initBundle();
    }

    /**
     * 这里方法的任务只是
     *
     * @param ack
     * @return
     */
    @Override
    protected Result dealAckInLocal(MoAckMsg ack) throws MOSException
    {
        //Result
        for (String dn : ack.dns())
        {
            IMoOperation operation = BundleOperationPool.pool.getOperation(new DN(dn), ack.getCmd());

            try
            {
                operation.ack(ack, this);
            }
            catch (Exception e)
            {
                logger(BundleDomain.class).error("fail to dealAckInLocal " + ack + "operation type" + operation.getClass().getSimpleName(), e);
            }
        }

        commit(ack.getTransactionID().getValue());
        return new Successful();
    }

    private SimpleDB<String, NeToBundleBind> getNeDb()
    {
        return neDb;
    }

    public void create(NeToBundleBind bind, int transId) throws MOSException
    {
        neTransaction.create(bind, transId);
    }

    public void update(NeToBundleBind bind, int transId) throws MOSException
    {
        neTransaction.update(bind, transId);
    }

    public void delete(String neId, int transId) throws MOSException
    {
        neTransaction.delete(neId, transId);
    }

    public void commit(int transId) throws MOSException
    {
        neTransaction.commit(transId);
    }

    public void rollback(int transId) throws MOSException
    {
        neTransaction.rollback(transId);
    }

    private void initBundle()
    {
        List<NeToBundleBind> neList;
        try
        {
            neList = neDb.getAll();
        }
        catch (MOSException e)
        {
            e.printStackTrace();
            neList = new ArrayList<NeToBundleBind>();
        }
        for (NeToBundleBind ne : neList)
        {
            log.debug("start to add to ems routing " + ne.neDN);
            EmsRoutingTable.service.addRoute(ne.neDN, this);
        }
    }

    void increaseNeNum()
    {
        neNum++;
    }

    void decreaseNeNum()
    {
        neNum--;
    }

    public boolean canManageMore()
    {
        return neNum < CAPACITY;
    }

    @Override
    public Result forward(MoMsg msg) throws MOSException
    {
        try
        {
            return this.getService().onMessage(msg);
        }
        catch (RemoteException e)
        {
            throw new MOSException(e);
        }
    }

    @Override
    public boolean localSupported(DN dn)
    {
        String key = new DNWrapper(dn.toString()).evenPos();
        return /*!dnObj.isSizeInOdd() && */BundleMIB.instance.contains(key);
    }

    @Override
    public boolean localOperable(DN dn, MoCmds cmd)
    {
        return BundleOperationPool.pool.isRegistered(dn, cmd);
    }

    @Override
    public FindResult broadcast(MoFindMsg msg)
    {
        try
        {
            return getService().broadcast(msg);
        }
        catch (Exception e)
        {
            return new FindResult(msg.getTransactionID());
        }
    }

    public String getSupportedVersion()
    {
        return supportedVersion;
    }

    public String getAddress()
    {
        return address;
    }

    public void setSupportedVersion(String supportedVersion)
    {
        this.supportedVersion = supportedVersion;
    }

    public void setAddress(String address) throws MOSException
    {
        this.address = address;
        this.service = null;
    }

    public BundleService getService() throws MOSException
    {
        if (service == null)
        {
            try
            {
                service = (BundleService) Naming.lookup(address);
            }
            catch (Exception e)
            {
                logger(BundleDomain.class).info("fail to look up address " + address + "service is null: " + (service == null));
                throw new MOSException(e);
            }
        }
        return service;
    }

    @Override
    public void onTimeOut(TransactionContext context) throws MOSException
    {
        rollback(context.getTransactionId());
    }
}
