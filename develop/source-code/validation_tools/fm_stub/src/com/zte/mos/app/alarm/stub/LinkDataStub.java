package com.zte.mos.app.alarm.stub;

import com.zte.ums.api.common.resource.ppu.ResourceException;
import com.zte.ums.api.common.resource.ppu.entity.LinkData;
import com.zte.ums.api.common.resource.ppu.entity.MocDefinition;

/**
 * Created by ccy on 9/16/15.
 */
public class LinkDataStub implements LinkData
{

    private String aPortId;
    private String aPortMoc;
    private String zPortId;
    private String zPortMoc;
    private String aNeId;
    private String zNeId;
    private String oid;
    private String moc;

    public LinkDataStub(String moc, String oid,
                        String aNeId, String aPortMoc, int aPortId,
                        String zNeId, String zPortMoc, int zPortId)
    {
        this.moc = moc;
        this.oid = oid;
        this.aNeId = aNeId;
        this.aPortMoc = aPortMoc;
        this.aPortId = String.valueOf(aPortId);
        this.zNeId = zNeId;
        this.zPortMoc = zPortMoc;
        this.zPortId = String.valueOf(zPortId);
    }



    @Override
    public String getAPortId() {
        return aPortId;
    }

    @Override
    public String getAPortMoc() {
        return aPortMoc;
    }

    @Override
    public String getZPortId() {
        return zPortId;
    }

    @Override
    public String getZPortMoc() {
        return zPortMoc;
    }

    @Override
    public String getANeId() {
        return aNeId;
    }

    @Override
    public String getZNeId() {
        return zNeId;
    }

    @Override
    public int getDirection() {
        return 0;
    }

    @Override
    public int getState() {
        return 0;
    }

    @Override
    public int[] getWorkState() {
        return new int[0];
    }

    @Override
    public int getManagementState() {
        return 0;
    }

    @Override
    public String[] getAttrValuesByNames(String[] strings) throws ResourceException {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String[] getSubTypes() {
        return new String[0];
    }

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public String getMoc() {
        return moc;
    }

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getAttrValueByName(String s) throws ResourceException {
        return null;
    }

    @Override
    public String getManagedBy() {
        return null;
    }

    @Override
    public String getBaseMoc() {
        return null;
    }

    @Override
    public String getLocateId() {
        return null;
    }

    @Override
    public String getParentLocateId() {
        return null;
    }

    @Override
    public String getParentOid() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "dispaly";
    }

    @Override
    public MocDefinition getDefinition() {
        return null;
    }
}
