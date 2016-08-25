package com.zte.mos.type;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.validators.BasicValidator;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.tools.JsonUtil;
import com.zte.mos.util.tools.StringUtil;
import org.apache.commons.beanutils.Converter;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luoqingkai on 15-4-22.
 */
@JsonSerialize(using = IPV4AddressSerializer.class)
public class IPV4Address extends BasicValidator implements Convertable, Serializable, Comparable
{
    public IPV4Address(Short[] ipAddress)
    {
        if (ipAddress == null || ipAddress.length != 4)
            try
            {
                throw new RuntimeException(
                        "the input value " + JsonUtil.toNode(
                                ipAddress).toString() + " is not ipv4 address");
            }
            catch (MOSException e)
            {
            }
        this.ipAddress = ipAddress;
    }

    public IPV4Address(String ip)
    {
        if (ip.isEmpty()){
            return;
        }
        if (!ip.matches("([\\d]+)\\.([\\d]+)\\.([\\d]+)\\.([\\d]+)"))
        {
            throw new RuntimeException("the input value " + ip + " is not in form of ipv4");
        }

        List<String> ipSecs = StringUtil.absPattern(ip, "([\\d]+)");
        if (!(ipSecs != null && ipSecs.size() == 4))
        {
            throw new RuntimeException("the input value " + ip + " is not in form of ipv4");
        }

        this.ipAddress = new Short[4];

        for (int i = 0; i < ipSecs.size(); i++)
        {
            ipAddress[i] = Short.parseShort(ipSecs.get(i));
            if (!(ipAddress[i] >= 0 && ipAddress[i] <= 255))
            {
                throw new RuntimeException("the section " + (i + 1) + " in " + ip + " is out of range");
            }
        }
    }

    protected IPV4Address()
    {
    }

    public Short[] ipAddress;

    public static Validator validator()
    {
        return new IPV4Address();
    }

    public String toString()
    {
        if (this.ipAddress == null){
            return "";
        }
        StringBuilder builder = new StringBuilder(16);
        int[] ipArray = toPositiveArray();
        for (int i = 0; i < 3; i++)
        {
            builder.append(ipArray[i]).append(".");
        }
        builder.append(ipArray[3]);
        return builder.toString().trim();
    }

    private int[] toPositiveArray()
    {
        int[] array = new int[4];
        for (int i = 0; i < 4; i++)
        {
            if (ipAddress[i] < 0)
            {
                array[i] = 127 - ipAddress[i];
            }
            else
            {
                array[i] = ipAddress[i];
            }
        }
        return array;
    }

    @Override
    public Converter getConverter()
    {
        return new IPV4AddressConvertor();
    }

    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {
        String ip = String.valueOf(value);
        try
        {
            new IPV4Address(ip);
        }
        catch (AssertionError e)
        {
            throw new ValidateException(
                    "field " + attr.field() + " with value " + ip + " is not " +
                            "a proper IpV4");
        }
    }

    @Override
    public int compareTo(Object o)
    {
        String targetIp = String.valueOf(o);
        if (!ipMach(targetIp))
        {
            return 0;
        }
        String[] strTargetIp = targetIp.split("\\.");
        for (int i = 0; i < strTargetIp.length; i++)
        {
            if (ipAddress[i] > Short.parseShort(strTargetIp[i]))
            {
                return 1;
            }
            else if (ipAddress[i] == Short.parseShort(strTargetIp[i]))
            {
                continue;
            }
            else if (ipAddress[i] < Short.parseShort(strTargetIp[i]))
            {
                return -1;
            }
        }
        return 0;
    }

    private boolean ipMach(String str)
    {
        String strIp = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
        Pattern p = Pattern.compile(strIp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof IPV4Address))
        {
            return false;
        }

        IPV4Address other = (IPV4Address)o;

        if (ipAddress == null && other.ipAddress != null)
        {
            return false;
        }

        if (ipAddress != null && other.ipAddress == null)
        {
            return false;
        }

        for (int i = 0; i < 4; i++)
        {
            if (!ipAddress[i].equals(other.ipAddress[i]))
            {
                return false;
            }
        }
        return true;
    }
}
