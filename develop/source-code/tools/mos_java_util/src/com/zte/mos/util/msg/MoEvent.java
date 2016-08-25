package com.zte.mos.util.msg;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.tools.JsonUtil;

import java.util.HashMap;

/**
 * Created by luoqingkai on 14-10-31.
 */
public class MoEvent implements MoMsg
{
    private String dn;
    private HashMap<String, Object> content = new HashMap<String, Object>();
    private String jsonMsg;

    public MoEvent(String name)
    {
        this.dn = name;
    }

    public MoEvent(String name, String jsonMsg)
    {
        this.dn = name;
        this.jsonMsg = jsonMsg;
    }

    @Override
    public MoCmds getCmd()
    {
        return MoCmds.Event;
    }

    @Override
    public Maybe<Integer> getTransactionID()
    {
        return null;
    }

    @Override
    public void setTransId(Maybe<Integer> transId)
    {

    }

    @Override
    public String dn()
    {
        return dn;
    }

    @Override
    public String[] dns()
    {
        return new String[]{dn};
    }

    @Override
    public void setDN(String dn)
    {
        this.dn = dn;
    }

    public String getMsg()
    {
        return jsonMsg;
    }

    public HashMap<String, Object> getContent()
    {
        return content;
    }

    public MoEvent put(String name, Object value)
    {
        this.content.put(name, value);
        return this;
    }

    @Override
    public String toString()
    {
        if (content.size() > 0)
        {
            try
            {
                return this.getClass().getSimpleName() + "{" + "dn: " + dn + "\'" + " content: " + "\'" + JsonUtil.toString(
                        content) + "}";
            }
            catch (MOSException e)
            {
                return this.getClass().getSimpleName() + "{" + "dn: " + dn + "\'" + " content: 'cannot toJson'" + "\'" + "}";
            }
        }
        else if (null != jsonMsg)
        {
            return this.getClass().getSimpleName() + "{" + "dn: " + dn + "\'" + " json: " + "\'" + jsonMsg + "}";
        }
        else
        {
            return this.getClass().getSimpleName() + "{" + "dn: " + dn + "\'" + " moEvent is blank" + "}";
        }
    }
}
