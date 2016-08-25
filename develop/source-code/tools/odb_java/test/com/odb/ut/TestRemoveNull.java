package com.odb.ut;

import com.odb.database.Odb;
import com.odb.exception.RemoveNullObjectException;
import com.odb.ut.stub.Mo;
import com.odb.ut.stub.MoDB;
import com.zte.mos.exception.MOSException;
import org.junit.Test;

/**
 * Created by olinchy on 5/22/14 for MO_JAVA.
 */
public class TestRemoveNull
{
    @Test(expected = RemoveNullObjectException.class)
    public void testRemoveNull() throws MOSException
    {
        Odb<Mo> odb = MoDB.instance().startTransaction();

        odb.remove(new Mo("a"));

    }
}
