package com.zte.mos.persistent.database;


import com.zte.mos.persistent.Record;
import com.zte.mos.persistent.database.connectionpool.ConnectionPoolManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static com.zte.mos.util.Singleton.getInstance;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by xy on 15-1-7.
 */
public class TestPersistent {
    private static String keyWords;
    private static String tableName;
    private static Connection connection;

    @BeforeClass
    public static void start() throws Exception {
        keyWords = "'mw.nr8120=7'";
        tableName = "LXY_TEST_PERSISTENT1";
        connection = getInstance(ConnectionPoolManager.class).getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("delete from MW_CM.LXY_TEST_PERSISTENT1");
            statement.execute("insert into MW_CM.LXY_TEST_PERSISTENT1 (NEID, NETYPE, NENAME) values ('mw.nr8120=7','nr8120','nr8120')");
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

    }

    @Test
    public void testRead() throws Exception {
        String filter = "NETYPE = 'nr8120'";
        MoDBPersistentContext dbPersistentContext = new MoDBPersistentContext("'mw.nr8120=9'");
        List<Record> records = dbPersistentContext.readRecord(tableName, filter);
        for (Record record : records) {
            assertThat(String.valueOf(record.get("NENAME")), is("nr8120"));
        }
    }

    @Test
    public void testClear() throws Exception {
        MoDBPersistentContext dbPersistentContext = new MoDBPersistentContext(keyWords);
        List<Record> records_before = dbPersistentContext.readRecord(tableName, "");
        assertThat(records_before.size(), is(1));
        dbPersistentContext.clear();
        List<Record> records = dbPersistentContext.readRecord(tableName, "");
        assertThat(records.size(), is(0));
    }
}
