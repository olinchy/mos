package com.zte.mos.type;

import com.zte.mos.exception.MOSException;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by luoqingkai on 15-4-23.
 */
public class TestIPV4Address {
    @Test
    public void testToString() throws MOSException
    {
        IPV4Address myip = new IPV4Address(new Short[]{-1,0,1,-128});
        Assert.assertEquals("128.0.1.255", myip.toString());
    }

    @Test
    public void testConvert() throws IllegalAccessException, MOSException
    {
        ConvertUtils.register(new IPV4AddressConvertor(), IPV4Address.class);
        UTIPV4User user = new UTIPV4User();
        Field[] fields = user.getClass().getFields();
        for (Field field : fields) {
            field.set(user, ConvertUtils.convert(new byte[]{1, 1, 1, 1}, field.getType()));
        }
        Assert.assertEquals("1.1.1.1", user.myip.toString());
    }

    class UTIPV4User{
        public IPV4Address myip;

        UTIPV4User() throws MOSException
        {
            myip = new IPV4Address(new Short[]{0,0,0,0});
        }
    }
}
