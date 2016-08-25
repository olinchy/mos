package com.mos.lite.ut;

import com.fasterxml.jackson.databind.JsonNode;
import com.mos.lite.http_service.EMS;
import com.mos.lite.process_starter.Main;
import org.junit.BeforeClass;

/**
 * Created by olinchy on 15-12-28.
 */
public class TestAllSyncRequest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Main.main(new String[]{"NR8960A"});
    }

    @org.junit.Test
    public void test_all_sync_request() throws Exception
    {
        EMS ems = new EMS();

        JsonNode node = ems.allSyncRequest("");

        System.out.println(node.toString());
    }
}
