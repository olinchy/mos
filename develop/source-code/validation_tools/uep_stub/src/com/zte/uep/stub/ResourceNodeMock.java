package com.zte.uep.stub;

import com.zte.ums.api.common.resource.ppu.ResourceException;
import com.zte.ums.api.common.resource.ppu.entity.MocDefinition;
import com.zte.ums.api.common.resource.ppu.entity.NodeData;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by pavel on 15-10-16.
 */
public class ResourceNodeMock extends DefaultMutableTreeNode implements NodeData{
    private String name = null;
    private String oid = null;
    private String moc = null;
    private String ip = null;
    public ResourceNodeMock(String name,String oid,String moc,String ip){
        super(name);
        this.name = name;
        this.oid = oid;
        this.moc = moc;
        this.ip = ip;
    }

    public ResourceNodeMock addChild(String name,String oid,String moc){
        return addChild(name,oid,moc,null);
    }

    public ResourceNodeMock addChild(String name,String oid,String moc,String ip){
        ResourceNodeMock node = new ResourceNodeMock(name,oid,moc,ip);
        this.add(node);
        return node;
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
        return name;
    }

    @Override
    public String getAttrValueByName(String s) throws ResourceException {
        return "attrValue";
    }

    @Override
    public String getManagedBy() {
        return "managedBy";
    }

    @Override
    public String getBaseMoc() {
        String baseMoc = null;
        if (moc.startsWith("mw.nr")){
            baseMoc = MocDefinition.BASE_MOC_NE;
        }
        else if (moc.equals("EMS")){
            baseMoc = MocDefinition.BASE_MOC_EMS;
        }
        else if (moc.equals("Common Group")){
            baseMoc = MocDefinition.BASE_MOC_GROUP;
        }
        return baseMoc;
    }

    @Override
    public String getLocateId() {
        return "locateId";
    }

    @Override
    public String getParentLocateId() {
        return "parentLocateId";
    }

    @Override
    public String getParentOid() {
        return "parentOid";
    }

    @Override
    public String getDisplayName() {
        return "displayName";
    }

    @Override
    public MocDefinition getDefinition() {
        return null;
    }

    @Override
    public int[] getMcc() {
        return new int[0];
    }

    @Override
    public int[] getMnc() {
        return new int[0];
    }

    @Override
    public String getIpAddress() {
        return ip;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public String getSubType() {
        return null;
    }

    @Override
    public double getPosX() {
        return 0;
    }

    @Override
    public double getPosY() {
        return 0;
    }

    @Override
    public int getConnectionState() {
        return 0;
    }

    @Override
    public int getManagementState() {
        return 0;
    }

    @Override
    public int[] getWorkState() {
        return new int[0];
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean isMovable() {
        return false;
    }

    @Override
    public SimpleTimeZone getTimeZone() {
        return null;
    }

    @Override
    public TimeZone getLocalTimeZone() {
        return null;
    }

    @Override
    public String[] getAttrValuesByNames(String[] strings) throws ResourceException {
        return new String[0];
    }

    @Override
    public String[] getSubTypes() {
        return new String[0];
    }

    @Override
    public String[] getProducts() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getOriginalDisplayName() {
        return null;
    }
}
