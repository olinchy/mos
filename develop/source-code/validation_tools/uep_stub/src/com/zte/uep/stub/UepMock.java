package com.zte.uep.stub;

import com.zte.app.common.uep.component.UepComponent;
import com.zte.app.common.uep.service.SnmpServerService;
import com.zte.app.common.uep.service.UepServerService;
import com.zte.ums.api.common.resource.ppu.ResourceException;
import com.zte.ums.api.common.resource.ppu.entity.NodeData;
import com.zte.ums.api.common.resource.ppu.entity.ResourceChangeListener;
import com.zte.ums.api.common.resource.ppu.entity.ResourceTree;
import com.zte.ums.api.common.resource.ppu.entity.ResourceTreeFilter;
import com.zte.ums.api.common.resource.ppu.service.ResourcePPUClientService;
import com.zte.ums.api.common.resource.ppu.service.ResourcePPUServerService;
import com.zte.ums.api.usf.env.SystemEnvService;
import com.zte.ums.common.resource.common.tree.ResourceTreeContainer;
import com.zte.ums.uep.api.pfl.emb.EMBService;
import com.zte.ums.uep.api.pfl.embmml.EMBMmlService;
import com.zte.ums.uep.api.pfl.finterface.FClientService;
import com.zte.ums.uep.api.pfl.log.LogServerService;
import com.zte.ums.uep.api.pfl.mainframe.MainFrameService;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferClientService;
import com.zte.ums.uep.api.psl.filetransfer.FileTransferContext;
import com.zte.ums.uep.api.psl.systemmonitor.SystemMonitorService;
import com.zte.ums.uep.api.psl.systemsupport.SystemSupportService;
import com.zte.ums.uep.gui.component.CloseListener;
import com.zte.ums.uep.gui.component.RowNumberTable;
import com.zte.ums.uep.gui.component.ZXWorkSpace;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import sun.awt.NullComponentPeer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.io.File;

/**
 * Created by pavel on 15-9-15.
 */
public class UepMock {
//    private BottomBtnPanelMock panelMock = null;
    private IMocksControl mocksControl = null;

    public UepMock(){
    }

//    public BottomBtnPanelMock getBottomBtnPanelMock(){
//        return panelMock;
//    }

    private void setupResourcePPUClientService(){
        ResourceTree resourceTree = new ResourceTreeMock();
        ResourcePPUClientService resourcePPUClientService = mocksControl.createMock(ResourcePPUClientService.class);
        EasyMock.expect(resourcePPUClientService.queryMocIds(EasyMock.anyString(), (String[]) EasyMock.anyObject())).andReturn(null).anyTimes();
        ResourceTreeContainer resourceTreeContainer = mocksControl.createMock(ResourceTreeContainer.class);
        EasyMock.expect(resourceTreeContainer.getTreeWithAdvancedFilter()).andReturn(null).anyTimes();
        EasyMock.expect(resourceTreeContainer.getTree()).andReturn(resourceTree).anyTimes();
        EasyMock.expect(resourcePPUClientService.getResourceTree(EasyMock.anyObject(ResourceTreeFilter.class))).andReturn(resourceTreeContainer).anyTimes();
        EasyMock.expect(resourcePPUClientService.getDefinition(EasyMock.anyString(), (String[]) EasyMock.anyObject())).andReturn(null).anyTimes();
        resourcePPUClientService.addNodeChangeListener(EasyMock.anyObject(ResourceChangeListener.class));
        EasyMock.expectLastCall().anyTimes();
        UepServerService.setResourcePPUClientService(resourcePPUClientService);
    }

    private void setupResourcePPUServerService()
    {
        NodeData emsNode = mocksControl.createMock(NodeData.class);
        ResourcePPUServerService resourcePPUServerService = mocksControl.createMock(ResourcePPUServerService.class);
        try
        {
            EasyMock.expect(resourcePPUServerService.getLocalEms()).andReturn(emsNode).anyTimes();
            EasyMock.expect(resourcePPUServerService.getRoot()).andReturn(emsNode).anyTimes();
        }
        catch (ResourceException e)
        {
            e.printStackTrace();
        }
        EasyMock.expect(emsNode.getParentOid()).andReturn("").anyTimes();
        EasyMock.expect(emsNode.getOid()).andReturn("EMSOID").anyTimes();
        EasyMock.expect(emsNode.getName()).andReturn("EMSNAME").anyTimes();
        UepServerService.setResourcePPUServerService(resourcePPUServerService);
    }

    private void setupI18nService(){
        UepServerService.setI18nService(new I18nServiceMock());
    }

    private void setupShowMsgPaneService(){
//        UepServerService.setShowMsgPaneService(new ShowMsgPaneServiceMock());
    }

    private void setupSystemSupportService(){
        SystemSupportService systemSupportServiceMock = mocksControl.createMock(SystemSupportService.class);
        EasyMock.expect(systemSupportServiceMock.getFile(EasyMock.anyString())).andReturn(new File("conf/res/zte_logo.gif")).anyTimes();
        UepServerService.setSystemSupportService(systemSupportServiceMock);
    }

    private void setupMainFrameService(){
        MainFrameService mainFrameServiceMock = mocksControl.createMock(MainFrameService.class);
        EasyMock.expect(mainFrameServiceMock.getContainerFrame(EasyMock.anyString())).andReturn(null).anyTimes();
        mainFrameServiceMock.enableButton((AbstractButton[]) EasyMock.anyObject());
        EasyMock.expectLastCall().anyTimes();
        UepServerService.setMainFrameService(mainFrameServiceMock);
    }

    private void setupFClientService(){
        FClientService fClientServiceMock = mocksControl.createMock(FClientService.class);
        EasyMock.expect(fClientServiceMock.getUserName()).andReturn("pavel").anyTimes();
        UepServerService.setfClientService(fClientServiceMock);
    }

    private void setupUIToolKitService(){
//        UIToolKitService uiToolKitServiceMock = new UIToolkitServiceMock(); //mocksControl.createMock(UIToolKitService.class);
//        uiToolKitServiceMock.configButtonSize(anyObject(JButton[].class));
//        expectLastCall().anyTimes();
//        expect(uiToolKitServiceMock.getIcon(anyString())).andReturn(new ImageIcon()).anyTimes();
//        panelMock = new BottomBtnPanelMock();
//        expect(uiToolKitServiceMock.createOKCancelPanel((ActionListener) anyObject(), (ActionListener) anyObject()))
//                .andReturn(new OKPanelMock(panelMock)).anyTimes();
//        UepServerService.setUiToolKitService(uiToolKitServiceMock);
    }

    private void setupRowNumberTable(){
        RowNumberTable rowNumberTableMock = mocksControl.createMock(RowNumberTable.class);
        EasyMock.expect(rowNumberTableMock.getOriginalTable()).andReturn(new JTable()).anyTimes();
        rowNumberTableMock.revalidate();
        EasyMock.expectLastCall().anyTimes();
        rowNumberTableMock.updateUI();
        EasyMock.expectLastCall().anyTimes();
        UepComponent.setObject(RowNumberTable.class, rowNumberTableMock);
    }

    private void setupExportService(){
        UepServerService.setExportService(new ExportServiceMock());
    }

    private void setupFileTransferContextService(){
        FileTransferContextServiceMock fileTransferContextServiceMock = new FileTransferContextServiceMock();
        fileTransferContextServiceMock.addFileTransferContext("mwRM",new FileTransferContext("127.0.0.1",21,22,"mwRM","defaultPassword"));
//        UepServerService.setFileTransferContextService(fileTransferContextServiceMock);
    }

    private void setupZXWorkSpace(){
        ZXWorkSpace workSpaceMock = mocksControl.createMock(ZXWorkSpace.class);
        UepComponent.setObject(ZXWorkSpace.class, workSpaceMock);
        workSpaceMock.recordActiveWindowOrder();
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.setMaxWindowCount(EasyMock.anyInt());
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.addCloseListener(EasyMock.anyObject(CloseListener.class));
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.addChangeListener(EasyMock.anyObject(ChangeListener.class));
        EasyMock.expectLastCall().anyTimes();
        EasyMock.expect(workSpaceMock.getPeer()).andReturn(new NullComponentPeer()).anyTimes();
        EasyMock.expect(workSpaceMock.getPanelByID(EasyMock.anyString())).andReturn(new JPanel()).anyTimes();
        workSpaceMock.removePanel(EasyMock.anyObject(JPanel.class));
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.validate();
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.updateUI();
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.repaint();
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.addPanel(EasyMock.anyObject(JPanel.class), EasyMock.anyString(), EasyMock.anyObject(Icon.class), EasyMock.anyString(), EasyMock.anyString());
        EasyMock.expectLastCall().anyTimes();
        workSpaceMock.setSeletedPanel(EasyMock.anyObject(JPanel.class));
        EasyMock.expectLastCall().anyTimes();
    }

    private void setupFileTransferClientService(){
        FileTransferClientService fileTransferClientServiceMock = new FileTransferClientServiceMock();
        UepServerService.setFileTransferClientService(fileTransferClientServiceMock);
    }

    private void setupEmbMmlService(){
        EMBMmlService embMmlService = new EmbMmlServiceMock();
        UepServerService.setEmbMmlService(embMmlService);
    }

    private void setupSystemEnvService(){
        SystemEnvService systemEnvService = new SystemEnvServiceMock();
        UepServerService.setSystemEnvService(systemEnvService);
    }

    private void setupEmbService(){
        EMBService embService = new EmbServiceMock();
        UepServerService.setEmbService(embService);
    }

    private void setupSnmpServerService(){
        SnmpServerService snmpServerService = new SnmpServerServiceMock();
        UepServerService.setSnmpServerService(snmpServerService);
    }

    private void setupLogServerService(){
        LogServerService logServerService = new LogServerServiceMock();
        UepServerService.setLogServerService(logServerService);
    }

    private void setupSystemMonitorService(){
        SystemMonitorService systemMonitorService = new SystemMonitorServiceMock();
        UepServerService.setSystemMonitorService(systemMonitorService);
    }

    private void setupImageIconService(){
//        UepServerService.setImageIconService(new ImageIconServiceMock());
    }

    public void setup(){
        mocksControl = EasyMock.createControl();
        setupExportService();
        setupFClientService();
        setupFileTransferContextService();
        setupFileTransferClientService();
        setupI18nService();
        setupMainFrameService();
        setupResourcePPUClientService();
        setupResourcePPUServerService();
        setupRowNumberTable();
        setupShowMsgPaneService();
        setupSystemSupportService();
        setupUIToolKitService();
        setupZXWorkSpace();
        setupImageIconService();
        setupEmbMmlService();
        setupSystemEnvService();
        setupEmbService();
        setupSnmpServerService();
        setupLogServerService();
        setupSystemMonitorService();
        mocksControl.replay();
    }

    public void teardown(){
        UepServerService.setExportService(null);
        UepServerService.setI18nService(null);
        UepServerService.setFileTransferContextService(null);
        UepServerService.setFileTransferClientService(null);
        UepServerService.setSystemSupportService(null);
        UepServerService.setMainFrameService(null);
        UepServerService.setfClientService(null);
        UepServerService.setResourcePPUClientService(null);
        UepServerService.setResourcePPUServerService(null);
        UepServerService.setImageIconService(null);
        UepServerService.setEmbMmlService(null);
        UepServerService.setSystemEnvService(null);
        UepServerService.setEmbService(null);
        UepServerService.setSnmpServerService(null);
        UepComponent.setObject(RowNumberTable.class, null);
        UepComponent.setObject(ZXWorkSpace.class,null);
        UepServerService.setLogServerService(null);
        UepServerService.setSystemMonitorService(null);
    }

}
