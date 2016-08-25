package com.zte.mos.msg;

import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.or.MoOrMapper;
import com.zte.mos.domain.or.ORDB;
import com.zte.mos.domain.or.ORField;
import com.zte.mos.domain.or.ORRow;
import com.zte.mos.msg.impl.snmp.tofile.DBRecord;
import com.zte.mos.msg.impl.snmp.tofile.DBRecords;
import org.hsqldb.types.NullType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olinchy on 16-1-28.
 */
public class TestToFile
{
    @Test
    public void test() throws Exception
    {
        MoOrMapper mapper = new MoOrMapper()
        {
            @Override
            public String getTableName()
            {
                return "test1";
            }

            @Override
            protected String buildDN(ORRow row, ORDB db)
            {
                return null;
            }

            @Override
            public Class getMoClass()
            {
                return NullType.class;
            }

            @Override
            protected ManagementObject buildMo(ORRow row)
            {
                return null;
            }

            @Override
            protected void setAttributes(
                    ManagementObject mo, ORRow row, ORDB db)
            {

            }

            @Override
            protected List<ORField> getFields(ManagementObject mo, ImagedSystem sys, int transId)
            {
                ArrayList<ORField> fields = new ArrayList<ORField>();
                for (int i = 0; i < 10; i++)
                {
                    fields.add(new ORField("field_" + i, "value_" + i));
                }
                return fields;
            }
        };

        DBRecords dbRecords = new DBRecords();

        DBRecord record = new DBRecord();
        record.table = "testTable";
        record.opType = "Update";
        List<ORRow> rows = mapper.toRow(null, null, 0);
        record.init(rows.get(0));

        dbRecords.records.add(record);

        dbRecords.write("./test.xml");
    }
}
