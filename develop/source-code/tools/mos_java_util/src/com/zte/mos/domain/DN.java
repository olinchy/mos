package com.zte.mos.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zte.mos.exception.NonvalidKeywordException;
import com.zte.mos.message.DNJsonSerializer;

import java.io.Serializable;

import static com.zte.mos.util.basic.Logger.logger;

@JsonSerialize(using = DNJsonSerializer.class)
public class DN implements Serializable, Comparable<DN>
{
    public DN(String dn)
    {
        if (isDN(dn))
            wrapper = new DNWrapper(dn);
    }

    private static final long serialVersionUID = 1L;
    private DNWrapper wrapper;

    public static boolean isDN(String dnString)
    {
        return DNWrapper.is(dnString);
    }

    public DN parent()
    {
        return new DN(wrapper.parent());
    }

    public DN to(int index)
    {
        return new DN(wrapper.to(index));
    }

    public String value(int index)
    {
        return wrapper.value(index);
    }

    /**
     * @param keyword
     * @return new DN('/'**'/'keyword'/'*)
     * @throws NonvalidKeywordException
     */
    // new DN("/**/keyword/*")
    public DN to(String keyword) throws NonvalidKeywordException
    {
        return new DN(wrapper.to(keyword));
    }

    public String getHead(String type) throws NonvalidKeywordException
    {
        return wrapper.to(type);
    }

    public String value(String type)
    {
        return wrapper.value(type);
    }

    public DN append(String value)
    {
        return new DN(wrapper.append(value));
    }

    @Override
    public int compareTo(DN o)
    {
        return this.wrapper.compareTo(o.wrapper);
    }

    public boolean deeperThan(DN dn)
    {
        return wrapper.deeperThan(dn.wrapper);
    }

    public boolean isOffspringOf(DN dn)
    {
        return this.wrapper.isOffspringOf(dn.wrapper);
    }

    @Override
    public int hashCode()
    {
        return wrapper != null ? wrapper.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DN dn = (DN) o;

        return !(wrapper != null ? !wrapper.equals(dn.wrapper) : dn.wrapper != null);
    }

    @Override
    public String toString()
    {
        if (wrapper == null)
        {
            return "";
        }
        return wrapper.to(-1);
    }
}


