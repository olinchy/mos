package com.zte.mos.modb.stub;

import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 6/24/14 for MO_JAVA.
 */
public class Ems extends BaseManagementObject
{
    public String dn = "/Ems/1";
    @MoChild()
    @MoSet(count = 1000)
    public GroupOf1000NesInEms ne = new GroupOf1000NesInEms(this, "Ne");

    public Ems()
    {
    }

    @Override
    public DN dn()
    {
        return new DN("/Ems/1");
    }

    @Override
    public void setDn(String dn)
    {

    }

}
