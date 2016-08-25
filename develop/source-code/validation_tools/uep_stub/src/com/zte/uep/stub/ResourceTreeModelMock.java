package com.zte.uep.stub;

import com.zte.ums.api.common.resource.ppu.entity.IResourceTreeModel;
import com.zte.ums.api.common.resource.ppu.entity.NodeData;
import com.zte.ums.api.common.resource.ppu.entity.ResourceData;

import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pavel on 15-10-16.
 */
public class ResourceTreeModelMock extends DefaultTreeModel implements IResourceTreeModel{
    private List<ResourceNodeMock> nodeList = null;
    private ResourceNodeMock treeRoot = null;
    public ResourceTreeModelMock(){
        super(new ResourceNodeMock("EMS","emsOid","emsMoc",null));
        treeRoot = (ResourceNodeMock)root;
        nodeList = new ArrayList<ResourceNodeMock>();

//        ResourceNodeMock groupNode = new ResourceNodeMock("GROUP","2","GROUP_MOC");
//        ResourceNodeMock neNode = new ResourceNodeMock("NE","3","mw.nr8250");
//        groupNode.add(neNode);
//        treeRoot.add(groupNode);
    }

//    public DefaultTreeModel getTreeModel() {
//        return treeModel;
//    }

    @Override
    public ResourceData getTreeRoot() {
        return treeRoot;
    }

    @Override
    public ResourceData findResourceData(String s) {
        refreshModel(treeRoot);
        for (ResourceNodeMock node:nodeList){
            if (node.getOid().equals(s)){
                return node;
            }
        }
        return null;
    }

    @Override
    public ResourceData findResourceData(String s, String s1) {
        return null;
    }

    @Override
    public ResourceData[] findResourceDatas(String s) {
//        refreshModel(treeRoot);
//        return nodeList.toArray(new ResourceData[nodeList.size()]);
        return new ResourceData[0];
    }

    private void refreshModel(ResourceNodeMock node){
        nodeList.add(node);
        if (node.isLeaf()){
            return;
        }
        else{
            for (int i=0;i<node.getChildCount();i++){
                refreshModel((ResourceNodeMock)node.getChildAt(i));
            }
        }
    }

    @Override
    public int getChildCount(ResourceData resourceData) {
        return 0;
    }

    @Override
    public ResourceData[] getChildren(ResourceData resourceData) {
        ResourceNodeMock node = (ResourceNodeMock)resourceData;
        ResourceData[] children = new ResourceNodeMock[node.getChildCount()];
        for (int i=0;i<node.getChildCount();i++){
            children[i] = (ResourceNodeMock)node.getChildAt(i);
        }
        return children;
    }

    @Override
    public ResourceData getParent(ResourceData resourceData) {
        return (ResourceNodeMock)((ResourceNodeMock)resourceData).getParent();
    }

    private void breadthFirstEnumeration(ResourceData resourceData,List<ResourceData> resourceDataList){
        ResourceNodeMock node = (ResourceNodeMock)resourceData;
        if (node.getChildCount()==0){
            return;
        }
        for (int i=0;i<node.getChildCount();i++){
            ResourceNodeMock nodeMock = (ResourceNodeMock)node.getChildAt(i);
            resourceDataList.add(nodeMock);
            breadthFirstEnumeration(nodeMock,resourceDataList);
        }
    }

    @Override
    public ResourceData[] breadthFirstEnumeration(ResourceData resourceData) {
        List<ResourceData> resourceDataList = new ArrayList<ResourceData>();
        breadthFirstEnumeration(resourceData,resourceDataList);

        ResourceData[] children = new ResourceNodeMock[resourceDataList.size()];
        for (int i=0;i<children.length;i++){
            children[i] = resourceDataList.get(i);
        }
        return children;
    }

    @Override
    public ResourceData[] breadthFirstEnumeration(String s, String s1) {
        return new ResourceData[0];
    }

    @Override
    public ResourceData[] extensionFilter(ResourceData[] resourceDatas) {
        return new ResourceData[0];
    }

    @Override
    public boolean isLeaf(ResourceData resourceData) {
        return ((ResourceNodeMock)resourceData).isLeaf();
    }

    @Override
    public void release() {

    }

    @Override
    public NodeData[] breadthFirstEffectiveNode(String s, String s1) {
        return new NodeData[0];
    }

}
