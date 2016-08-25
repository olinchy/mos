package com.zte.mos.storage.triggers;

import com.odb.database.BerkeleyDBIndexer;
import com.odb.database.BerkeleyDBObjectIndexer;
import com.odb.database.Key;
import com.odb.database.ODBWalker;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.zte.mos.domain.DN;
import com.zte.mos.domain.DNWrapper;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.MetaStringStore;
import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.tools.Expression;

import java.util.ArrayList;

import static com.zte.mos.service.cmd.FormatReference.parseMoClass;
import static com.zte.mos.util.Singleton.getInstance;

/**
 * Created by olinchy on 15-7-13.
 */
public class MoWalker implements ODBWalker<ManagementObject>
{
    private final String who;
    private final String under;
    private final Expression expression;
    private final MOS mos;
    private ArrayList<ManagementObject> localList = new ArrayList<ManagementObject>();

    public MoWalker(String who, DN under, Expression expression, MOS mos)
    {
        this.who = who;
        this.under = under.toString();
        this.expression = expression;
        this.mos = mos;
    }

    @Override
    public void walk(BerkeleyDBIndexer<Key, ManagementObject> moIndexer) throws MOSException
    {
        if (who.equals(""))
        {
            all((BerkeleyDBObjectIndexer<Key, ManagementObject>) moIndexer);
        }
        else
        {
            whoseClassIs(who, (BerkeleyDBObjectIndexer<Key, ManagementObject>) moIndexer, under);

            if (localList.isEmpty())
            {
                whoseParentNameIs(who, (BerkeleyDBObjectIndexer<Key, ManagementObject>) moIndexer, under);
            }

            if (localList.isEmpty())
            {
                whoseSuperClassIs(who, (BerkeleyDBObjectIndexer<Key, ManagementObject>) moIndexer, under);
            }
        }
    }

    private void all(BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer) throws MOSException
    {
        CursorConfig config = new CursorConfig();
        config.setReadUncommitted(true);
        Cursor cursor = null;
        try
        {

            cursor = moIndexer.getDatabase().openCursor(null, config);
            DatabaseEntry keyEntry = new DatabaseEntry();
            DatabaseEntry dataEntry = new DatabaseEntry();
            while (cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS)
            {
                matchLocal(moIndexer, under, dataEntry);
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }

    private void whoseClassIs(
            String who, BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer, String under) throws MOSException
    {
        whoIs(who, moIndexer, under, "className");
    }

    private void whoseSuperClassIs(
            String who, BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer, String under) throws MOSException
    {
        whoIs(who, moIndexer, under, "superClass");
    }

    private void whoseParentNameIs(
            String who, BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer, String under) throws MOSException
    {
        whoIs(who, moIndexer, under, "moType");
    }

    @Override
    public boolean contains(ManagementObject data)
    {
        return this.localList.contains(data);
    }

    @Override
    public void remove(ManagementObject data)
    {
        this.localList.remove(data);
    }

    @Override
    public void add(ManagementObject data) throws MOSException
    {
        String superClassName = data.getClass().getSuperclass().getSimpleName();
        String className = data.getClass().getSimpleName();
        String parentName = data.isGroup()? "Group": new DNWrapper(data.dn().toString()).type();

        if (superClassName.equals(who) || className.equals(who) || parentName.equals(who))
        {
            this.localList.add(data);
        }
    }

    private void whoIs(
            String who, BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer, String under,
            String secondaryKey) throws MOSException
    {
        Cursor cursorMoType = null;
        try
        {
            SecondaryDatabase seDB = moIndexer.getSecondaryIndex(secondaryKey);

            CursorConfig config = new CursorConfig();
            config.setReadUncommitted(true);

            cursorMoType = seDB.openCursor(null, config);
            DatabaseEntry keyEntry = new DatabaseEntry();
            DatabaseEntry dataEntry = new DatabaseEntry();
            StringBinding.stringToEntry(who, keyEntry);
            if (!OperationStatus.SUCCESS.equals(cursorMoType.getSearchKey(keyEntry, dataEntry, LockMode.DEFAULT)))
                return;
            matchLocal(moIndexer, under, dataEntry);
            while (OperationStatus.SUCCESS.equals(cursorMoType.getNextDup(keyEntry, dataEntry, LockMode.DEFAULT)))
            {
                matchLocal(moIndexer, under, dataEntry);
            }
        }
        finally
        {
            if (cursorMoType != null)
                cursorMoType.close();
        }
    }

    private void matchLocal(
            BerkeleyDBObjectIndexer<Key, ManagementObject> moIndexer, String under,
            DatabaseEntry dataEntry) throws MOSException
    {
        ManagementObject mo = moIndexer.entryToObject(dataEntry);

        Mo moClass = mo.toMoClass().setDn(mo.dn());
        mo.setMos(mos);
        parseMoClass(mos, mo, moClass, new Maybe<Integer>(null));

        if (mo.dn().isOffspringOf(new DN(under)) && expression.evaluate(moClass, null))
        {
            mo.setDn(new DN(mos.getRootExternalDN()).append(mo.dn().toString()).toString());
            localList.add(mo);
        }
    }

    public boolean isLocal() throws MOSException
    {
        return getInstance(MetaStringStore.class).isIn(who) || who.equals("");
    }

    public ArrayList<ManagementObject> getLocal()
    {
        return this.localList;
    }
}
