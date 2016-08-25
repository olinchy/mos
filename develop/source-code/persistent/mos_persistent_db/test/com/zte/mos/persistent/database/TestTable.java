package com.zte.mos.persistent.database;

import com.zte.mos.persistent.Record;
import com.zte.mos.persistent.database.stub.StubConnection;
import com.zte.mos.persistent.database.stub.StubResultSet;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by xy on 15-1-4.
 */
public class TestTable {
    private static HashMap<String, String> map = new HashMap<String, String>();
    private static ResultSet resultSet;
    private static Connection connection;

    @BeforeClass
    public static void start() throws SQLException {
        map.put("A", "a");
        map.put("B", "b");
        map.put("C", "c");
        map.put("D", "d");
        map.put("E", "e");
        map.put("F", "f");
        resultSet = new StubResultSet(map);
        resultSet.next();
        connection = new StubConnection(new String[]{"a", "b"}, map.keySet());
    }

    @Test
    public void test() throws Exception {
        // todo depends
        Table table = new Table("test", connection);
        assertThat(table.getKeys(), is(new String[]{"a", "b"}));
        Record record = table.readLine(resultSet);
        for (Map.Entry<String, String> o : map.entrySet()) {
            assertThat(String.valueOf(record.get(o.getKey())), is(o.getValue()));
        }
    }

}
