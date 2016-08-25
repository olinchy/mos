package com.zte.mos.msg.impl.snmp.reverse;

/**
 * Created by ccy on 3/3/16.
 */
public class ReverseResult
{
    public int getResult()
    {
        return result;
    }

    public String getFilePath()
    {
        return filePath;
    }

    private final int result;
    private final String filePath;

    public ReverseResult(int result, String path)
    {
        this.result = result;
        this.filePath = path;
    }

    public boolean isSuccess()
    {
        return result == 0;
    }

}
