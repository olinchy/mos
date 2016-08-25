package com.zte.mos.imaged;

import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.exception.NullMoException;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.service.RawMos;
import com.zte.mos.util.basic.Logger;
import org.eclipse.jetty.util.ConcurrentHashSet;

import java.util.LinkedList;

import static com.zte.mos.util.basic.Logger.logger;


public class ReverseAllSynchor
{
    private static Logger log = logger(ReverseAllSynchor.class);

    private RawMos mos;
    private ConcurrentHashSet<DN> dnInDB = new ConcurrentHashSet<DN>();

    public ReverseAllSynchor(RawMos mos)
    {
        this.mos = mos;
    }

    public void clear()
    {
        dnInDB.clear();
    }

    private void initLocalDnSet() throws MOSException
    {
        LinkedList<BaseManagementObject> cache = new LinkedList<BaseManagementObject>();

        BaseManagementObject root = (BaseManagementObject) mos.getMO(MOS.ROOT_INTERNAL_DN, MOS.SYNC_TRANS_ID);
        cache.add(root);
        do
        {
            BaseManagementObject mo = (BaseManagementObject) mos.getMO(cache.removeFirst().dn().toString(), MOS.SYNC_TRANS_ID);
            if (mo == null)
            {
                continue;
            }

            ManagementObject[] children = mo.ls();
            for (ManagementObject child : children)
            {
                cache.addLast((BaseManagementObject) child);
                dnInDB.add(child.dn());
            }
        } while (!cache.isEmpty());
    }

    public void save(ManagementObject[] section, int sequence) throws MOSException
    {

        initLocalDnSet();

        for (ManagementObject mo : section){
            checkMO((BaseManagementObject)mo);
        }

        deleteRedundantMO();
        dnInDB.clear();
        // commit();
    }

    private void deleteRedundantMO() throws MOSException
    {
        for (DN dn : dnInDB)
        {
            if (mos.getMO(dn.toString(), MOS.SYNC_TRANS_ID) != null)
            {
                try{
                    log.info("start del redundant:" + dn.toString());
                    mos.deleteMO(dn.toString(), MOS.SYNC_TRANS_ID);
                    //deleted.add(dn);
                    log.info("del redundant:" + dn.toString());
                }catch (MOSException e){
                    if(e.getCause() instanceof NullMoException){
                        continue;
                    }
                    else{
                        throw e;
                    }
                }
            }
        }
    }

    public LinkedList<Log<ManagementObject>> commit() throws MOSException
    {
        return mos.commit(MOS.SYNC_TRANS_ID);
    }


    private void add(BaseManagementObject mo) throws MOSException
    {
        boolean isNull = mos.getMO(mo.dn, MOS.SYNC_TRANS_ID) == null;
        if (isNull)
        {
            mos.createMO(mo, MOS.SYNC_TRANS_ID);
            log.info("add:" + mo.dn);
        }
    }

    private void set(BaseManagementObject mo) throws MOSException
    {
        mos.updateMO(mo, null, MOS.SYNC_TRANS_ID);
        log.info("set:" + mo.dn);
    }

    private void delete(BaseManagementObject mo) throws MOSException
    {
        mos.deleteMO(mo.dn, MOS.SYNC_TRANS_ID);
        log.info("del:" + mo.dn);
    }

    private void checkMO(BaseManagementObject mo) throws MOSException
    {
        DN dn = mo.dn();
        BaseManagementObject my = (BaseManagementObject) mos.getMO(dn.toString(), MOS.SYNC_TRANS_ID);
        if (my == null)
        {
            add(mo);
            return;
        }
        else
        {
            if (!my.deepEquals(mo))
            {
                if (mo.isKeyAttributeEquals(my))
                {
                    set(mo);
                }
                else
                {
                    log.info(mo.dn + " replacing...");
                    delete(my);
                    add(mo);
                }
            }
            dnInDB.remove(mo.dn());
        }
    }
}
