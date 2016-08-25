package com.zte.uep.stub;

import com.zte.ums.api.common.resource.ppu.entity.IResourceTreeModel;
import com.zte.ums.api.common.resource.ppu.entity.ResourceData;
import com.zte.ums.api.common.resource.ppu.entity.ResourceTree;
import com.zte.ums.api.common.resource.ppu.extension.client.ResourceToolTipProvider;

import javax.swing.*;

/**
 * Created by pavel on 15-10-16.
 */
public class ResourceTreeMock extends ResourceTree{

    protected ResourceTreeMock(){
        super(new ResourceTreeModelMock());
    }
//    protected ResourceTreeMock(ResourceTreeModelMock resourceTreeModel) {
//        super(resourceTreeModel.getTreeModel());
//    }

    @Override
    public IResourceTreeModel getResourceModel() {
        return (IResourceTreeModel)super.getModel();
    }

    @Override
    public ResourceData[] getSelectedData() {
        return new ResourceData[]{(ResourceData)this.getLastSelectedPathComponent()};
    }

    @Override
    public void selectData(ResourceData[] resourceDatas) {

    }

    @Override
    public void setToolTipProvider(ResourceToolTipProvider resourceToolTipProvider) {

    }

    @Override
    public JMenuItem[] getExpandAndCollapseJMenuItem(ResourceData resourceData) {
        return new JMenuItem[0];
    }
}
