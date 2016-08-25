package com.zte.mos.modb.stub;

import com.zte.mos.annotation.MoChild;
import com.zte.mos.annotation.MoSet;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 6/24/14 for MO_JAVA.
 */
public class Ne extends BaseManagementObject
{
    public String dn = "/Ems/1/Ne/1";
    public String version;
    @MoChild()
    @MoSet(count = 10)
    public GroupOf10ShelfsInNe shelves = new GroupOf10ShelfsInNe(this, "Shelf");
    @MoChild()
    @MoSet(count = 300)
    public GroupOf300P2pRoutesInNe routes = new GroupOf300P2pRoutesInNe(this, "P2pRoute");

    @Override
    public DN dn()
    {
        return new DN("/Ems/1/Ne/1");
    }

    @Override
    public void setDn(String dn)
    {

    }

}
