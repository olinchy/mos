package com.zte.mos.tools.ut;

import com.zte.mos.persistent.Record;
import com.zte.mos.tools.ut.stub.MoToRecord;
import com.zte.mos.util.tools.ToRecord;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 12/29/14 for mosjava.
 */
public class TestToRecord
{

    @Test
    public void test() throws Exception
    {
        MoToRecord mo = new MoToRecord();
        Record record = ToRecord.toRecord(mo);

        assertThat(String.valueOf(record.get("index")), is("1"));
        assertThat(String.valueOf(record.get("value")), is("2"));

    }
}
