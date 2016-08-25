package com.zte.mos.modb.stub;

import com.zte.mos.domain.BaseManagementObject;
import com.zte.mos.domain.DN;

/**
 * Created by app on 14-6-10.
 */

public class Mo extends BaseManagementObject
{
    public String id;

    @SuppressWarnings("unused")
    public Mo()
    {
    }

    public Mo(DN dn)
    {
        this.dn = dn.toString();
        this.id = String.valueOf((int) dn.toString().toCharArray()[0]);
    }

    public Mo(DN dn, String id)
    {
        this.dn = dn.toString();
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        Mo data = (Mo) o;

        return id.equals(data.id) && dn.equals(data.dn);

    }
}
