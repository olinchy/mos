package com.zte.mos.storage;

import com.odb.database.Key;
import com.odb.database.Odb;
import com.odb.database.extended_odb.CommittedODB;
import com.odb.database.extended_odb.OperatingSet;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.persistent.Log;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.triggers.MoIndexer;

import java.lang.reflect.Field;
import java.util.List;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by root on 14-12-2.
 * A DB supports triggers which is used by MOS.
 */
public class MODB extends OdbOperation
{
    private final MOS mos;
    private Revision revision;
    private TriggerRegister register;

    public MODB(TriggerRegister register, MOS mos)
    {
        primaryIndex = new MoIndexer(mos.getRootExternalDN());
        OperatingSet<ManagementObject> operatingSet = new OperatingSet<ManagementObject>(primaryIndex);
        odb = new CommittedODB<ManagementObject>(primaryIndex, operatingSet);

        this.register = register;
        this.register.register(odb);
        revision = new Revision();
        this.mos = mos;
    }

    public MODB(TriggerRegister register)
    {
        this(register, null);
    }

    public MosTransaction newTransaction(int transID) throws MOSException
    {
        Odb<ManagementObject> transOdb;
        if (transID < 0)
        {
            transOdb = odb.startTransaction();
            return new MosSyncTransaction(transID, transOdb, revision, mos);
        }
        else
        {
            transOdb = odb.startTransaction();
            return new MosTransaction(transID, transOdb, revision, mos);
        }
    }

    public void reload() throws MOSException
    {
        clear();
    }

    public void stop()
    {
        odb.stop();
    }

    public void recover(ManagementObject mo) throws MOSException
    {
        ManagementObject origin;
        if ((origin = odb.get(new Key(mo.dn()))) != null)
        {
            for (Field field : mo.getClass().getFields())
            {
                if (field.isAnnotationPresent(MoAttribute.class))
                {
                    try
                    {
                        field.set(origin, field.get(mo));
                    }
                    catch (IllegalAccessException e)
                    {
                        logger(this.getClass()).warn(
                                "setting " + mo.getClass().toString() + "." + field.getName() + " in MODB.recover caught exception",
                                e);
                    }
                }
            }
            origin.setMos(((BaseManagementObject) mo).getMos());
            odb.set(origin);
        }
        else
            odb.add(mo);
    }

    private void clear() throws MOSException
    {
        odb.clearAll();
    }

    public int getRevision()
    {
        return this.revision.getDisplayRevision();
    }

    public void resetRevision()
    {
        this.revision.reset();
    }

    @Override
    public List<Log<ManagementObject>> commit() throws MOSException
    {
        return revision.commit(this.odb);
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(ManagementObject mo) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public long createMoInTransaction(ManagementObject e) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public RemovedMoInTrans removeMoInTransaction(DN k) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public long updateMoInTransaction(ManagementObject e) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public void commit(long operTransId) throws MOSException
    {
        throw new MOSException();
    }

    @Override
    public void rollback(long operTransId) throws MOSException
    {
        throw new MOSException();
    }

    public void regObjTriggers()
    {
        register.regObjectTriggers(odb);
    }

    public void renameWithPostfix(String postfix)
    {
        this.odb.renameWithPostfix(postfix);
    }

    public void delPostfix(String postfix)
    {
        this.odb.delPostfix(postfix);
    }
}
