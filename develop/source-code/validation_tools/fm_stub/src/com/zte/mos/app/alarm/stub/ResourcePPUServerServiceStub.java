package com.zte.mos.app.alarm.stub;

import com.zte.mos.util.Singleton;
import com.zte.ums.api.common.resource.ppu.ResourceException;
import com.zte.ums.api.common.resource.ppu.emb.NodeMsg;
import com.zte.ums.api.common.resource.ppu.entity.*;
import com.zte.ums.api.common.resource.ppu.query.ComponentAuthParam;
import com.zte.ums.api.common.resource.ppu.query.Scope;
import com.zte.ums.api.common.resource.ppu.query.osf.ComponentAsynOsfQueryCondition;
import com.zte.ums.api.common.resource.ppu.query.osf.Filter;
import com.zte.ums.api.common.resource.ppu.service.ResourcePPUServerService;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by ccy on 9/14/15.
 */
public class ResourcePPUServerServiceStub implements ResourcePPUServerService {
    @Override
    public NodeData[] queryNodeDatas(String s) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public ComponentData queryComponentData(String s, String s1) throws ResourceException {
        return null;
    }

    @Override
    public ComponentData[] queryComponentDatas(String s, String[] strings, String[] strings1) throws ResourceException {
        return new ComponentData[0];
    }

    @Override
    public NodeData[] queryNodeDatas(NodeData nodeData, Scope scope, Filter filter, String[] strings) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public NodeData[] queryRelationTableNodeDatas(String[] strings, Filter filter, String[] strings1) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public LinkData[] queryRelationTableLinkDatas(Filter filter, String[] strings) throws ResourceException {
        return new LinkData[0];
    }

    @Override
    public ComponentData[] queryRelationTableComponentDatas(String[] strings, Filter filter, String[] strings1) throws ResourceException {
        return new ComponentData[0];
    }

    @Override
    public ComponentData[] queryComponentDatas(String s, ResourceData resourceData, Scope scope, Filter filter, String[] strings) throws ResourceException {
        return new ComponentData[0];
    }

    @Override
    public void queryComponentDatasAsyn(ComponentAsynOsfQueryCondition componentAsynOsfQueryCondition, ComponentDataAsynQueryListener componentDataAsynQueryListener) throws ResourceException {

    }

    @Override
    public void unRegisterQueryComponentDatasListener(ComponentDataAsynQueryListener componentDataAsynQueryListener) {

    }

    @Override
    public LinkData[] queryLinkDatas(ResourceData resourceData, Scope scope, Filter filter, String[] strings) throws ResourceException {
        return new LinkData[0];
    }

    @Override
    public NodeData getRoot() throws ResourceException {
        return new NodeData() {
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
                return null;
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

            @Override
            public String getOid() {
                return "mw.nr8250=1";
            }

            @Override
            public String getMoc() {
                return "mw.nr8250";
            }

            @Override
            public String getName() {
                return null;
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
                return null;
            }

            @Override
            public MocDefinition getDefinition() {
                return null;
            }
        };
    }

    @Override
    public NodeData getLocalEms() throws ResourceException {
        return null;
    }

    @Override
    public ResourceData[] getAncestorResourceDatas(ResourceData resourceData) throws ResourceException {
        return new ResourceData[0];
    }

    @Override
    public String[] queryMocIds(String s, String[] strings) {
        return new String[0];
    }

    @Override
    public Definition getDefinition(String s) {
        return null;
    }

    @Override
    public Definition[] getDefinition(String[] strings) {
        return new Definition[0];
    }

    @Override
    public MocDefinition[] getMocByTableName(String s) {
        return new MocDefinition[0];
    }

    @Override
    public IMocTreeModel getMocTree(String[] strings) {
        return null;
    }

    @Override
    public MocDefinition[] getComponentMoc(String s, String s1) {
        return new MocDefinition[0];
    }

    @Override
    public MocDefinition[] getDefinition(String s, String[] strings) {
        return new MocDefinition[0];
    }

    @Override
    public NodeData[] queryManagedNodeDatas(String s, String[] strings, String[] strings1) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public NodeData[] queryNodeDatas(Filter filter, String[] strings) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public ComponentData[] queryComponentDatas(Filter filter, String[] strings) throws ResourceException {
        return new ComponentData[0];
    }

    @Override
    public LinkData[] queryLinkDatas(Filter filter, String[] strings) throws ResourceException
    {
        try
        {
            return Singleton.getInstance(LinkDataSimulatorDb.class).getAll();
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
        return new LinkData[0];
    }

    @Override
    public LinkData queryLinkDatas(String s, String[] strings) throws ResourceException {
        return null;
    }

    @Override
    public NodeData queryNodeDatas(String s, String[] strings) throws ResourceException {
        return null;
    }

    @Override
    public NodeData[] queryNodeDatas(String[] strings, String[] strings1) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public String getNodePathName(String s, String s1) throws ResourceException {
        return null;
    }

    @Override
    public String getComponentDisplayName(String s) throws ResourceException {
        return null;
    }

    @Override
    public String[] getComponentDisplayName(String[] strings) throws ResourceException {
        return new String[0];
    }

    @Override
    public int[] getNeIndex(String[] strings) throws ResourceException {
        return new int[0];
    }

    @Override
    public String[] getNeOid(int[] ints) throws ResourceException {
        return new String[0];
    }

    @Override
    public boolean componentAuth(String s, String s1, String s2, ComponentAuthParam componentAuthParam) throws ResourceException {
        return false;
    }

    @Override
    public MocDefinition[] getAncestorMoc(String s, String s1, String s2) {
        return new MocDefinition[0];
    }

    @Override
    public String[] getNodePathName(String[] strings, String[] strings1) throws ResourceException {
        return new String[0];
    }

    @Override
    public String[] getNodeDisplayName(String[] strings) throws ResourceException {
        return new String[0];
    }

    @Override
    public IResourceTreeModel getTreeModel(ResourceTreeFilter resourceTreeFilter) throws ResourceException {
        return null;
    }

    @Override
    public boolean isUseCoordinateOnOMM() throws ResourceException {
        return false;
    }

    @Override
    public boolean isLocalEmsNode(String s) throws ResourceException {
        return false;
    }

    @Override
    public NodeData getManagedEms(String s) throws ResourceException {
        return null;
    }

    @Override
    public NodeData[] getManagedEms(String[] strings) throws ResourceException {
        return new NodeData[0];
    }

    @Override
    public MocDefinition getInActiveMocDefinition(String s) {
        return null;
    }

    @Override
    public int compare(ComponentData componentData, ComponentData componentData1) {
        return 0;
    }

    @Override
    public void setLocalEmsDisplayName(String s) throws ResourceException {

    }

    @Override
    public MappingMocDefinition getMappingMocDefinitionByMocAndSubType(String s, String[] strings, String s1) throws ResourceException {
        return null;
    }

    @Override
    public MappingMocDefinition[] getMappingMocDefinitionByVMoc(String s, String s1) throws ResourceException {
        return new MappingMocDefinition[0];
    }

    @Override
    public ModeDefinition getModeDefinition(String s) throws ResourceException {
        return null;
    }

    @Override
    public ModeDefinition[] getAllModeDefinition() throws ResourceException {
        return new ModeDefinition[0];
    }

    @Override
    public MocDefinition[] getDefinition(String[] strings, String[] strings1, String s) throws ResourceException {
        return new MocDefinition[0];
    }

    @Override
    public BusinessResourceData[] queryBusinessResource(String[] strings) throws ResourceException {
        return new BusinessResourceData[0];
    }

    @Override
    public BusinessResourceData[] queryBusinessResource(long[] longs) throws ResourceException {
        return new BusinessResourceData[0];
    }

    @Override
    public NodeMsg convertNodeData2NodeMsg(NodeData nodeData) throws ResourceException {
        return null;
    }

    @Override
    public String[] getNodeOid(String s, boolean b) throws ResourceException {
        return new String[0];
    }

    @Override
    public NodeData[] queryNodeDataTreeAttrs(NodeData nodeData, String[] strings) throws ResourceException {
        return new NodeData[0];
    }
}
