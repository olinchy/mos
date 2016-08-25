package com.zte.mos.modb.stub;

import com.zte.mos.annotation.MoChild;
import com.zte.mos.domain.DN;

/**
 * Created by olinchy on 7/2/14 for MO_JAVA.
 */
public class Parent extends Mo
{
    @MoChild()
    public GroupOf1ChildsInParent chi = new GroupOf1ChildsInParent(this, "Child");

    public Parent()
    {

    }

    public Parent(DN dn)
    {
        super(dn);
    }

}
