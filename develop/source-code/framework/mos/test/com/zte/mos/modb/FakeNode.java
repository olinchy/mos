package com.zte.mos.modb;

import com.zte.mos.annotation.MoReference;
import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.service.cmd.DependencyPolicyInList;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-9-29.
 */
public class FakeNode extends BaseManagementObject
{
    @MoReference(field = "r_nes", type = "Ne", isMulti = true, under = DependencyPolicyInList.class)
    public ArrayList<String> nes = new ArrayList<String>();
}
