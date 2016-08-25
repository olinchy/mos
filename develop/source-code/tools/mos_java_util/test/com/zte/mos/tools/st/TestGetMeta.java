package com.zte.mos.tools.st;

import com.zte.mos.domain.DN;
import com.zte.mos.httpservice.MosServiceHttp;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.message.Result;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by olinchy on 16-4-8.
 */
public class TestGetMeta
{
    @Test
    public void test_get_meta() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        Result<MoMetaClass> meta = service.get_meta(new DN("/Ems/1/Ne/1/Product/1/Shelf/1"), "PortVlan_Details", false);

        assertThat(meta.getMo().get(0).className, is("PortVlan_Details"));

    }

    @Test
    public void test_get_meta_with_enum() throws Exception
    {
        MosServiceHttp service = new MosServiceHttp();
        Result<MoMetaClass> meta = service.get_meta(new DN("/Ems/1/Ne/"), "Ne", false);

        assertThat(meta.getMo().get(0).className, is("Ne"));
    }
}
