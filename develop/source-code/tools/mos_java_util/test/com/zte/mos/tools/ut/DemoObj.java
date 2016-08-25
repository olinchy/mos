package com.zte.mos.tools.ut;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

/**
 * Created by olinchy on 16-5-28.
 */
@JsonPropertyOrder(alphabetic = false)
public class DemoObj
{
    public Integer sid;
    public String stuName;
    public Boolean sex;
    public Date logTime;
}