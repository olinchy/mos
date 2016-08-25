package com.zte.mos.domain;

/**
 * Created by olinchy on 8/7/14 for MO_JAVA.
 */
public abstract class GroupOf extends BaseManagementObject
{
    public GroupOf()
    {
    }

    public GroupOf(ManagementObject parent, String thisName)
    {
        this.parent = parent;
        this.name = thisName;
    }

    public String name;
    private ManagementObject parent;

    @Override
    public boolean isGroup()
    {
        return true;
    }

    @Override
    public DN dn()
    {
        if (dn == null && parent != null)
        {
            dn = new DN(getParentDn() + "/" + name).toString();
        }

        return new DN(dn);
    }

    private String getParentDn()
    {
        return parent.dn().toString().equals("/") ? "" : parent.dn().toString();
    }
}
