package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.exception.MOSException;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 4/27/15.
 */
public class TestActionHttp
{
    @Test
    public void test() throws MOSException
    {
        MosServiceHttp service = new MosServiceHttp();
        Result<ActionRsp> res = service.act(new DN("/Ems/1"), "allocNeId");
        assertTrue(res.isSuccess());
        ActionRsp rsp = res.getMo().get(0);
        for (AttributeClass attribute : rsp.meta.attributes)
        {
            System.out.println(rsp.get(attribute.name));
        }
    }

//    Drawer drawer = new Drawer(
//            Constraint.Layout.set("form"),
//            Constraint.Exclude.add("dad", "asdad"),
//            Constraint.Include.add("dad", "asdad"),
//            Constraint.Binding.add(
//                    new Binding("dn", function () {} ),
//                    new Binding("version", function () {})
//    ));
//    JPanel panel = drawer.draw("/Ems/1/Ne", Type.Add, Contraint[]);
}
