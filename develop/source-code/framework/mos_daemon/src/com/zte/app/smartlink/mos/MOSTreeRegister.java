package com.zte.app.smartlink.mos;

import com.zte.mos.annotation.PreLoad;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.OperationMask.*;
import static com.zte.mos.util.Singleton.getInstance;

@PreLoad
public class MOSTreeRegister
{
    private final SmartLink tree = new SmartLink();

    public MOSTreeRegister() throws Exception
    {
        NodeLink node_ref = new NodeLink("/**");
        node_ref.addNode("MOS", ref);
        tree.addLink(node_ref);

        NodeLink node_bundle = new NodeLink("/Ems/1/Ne/*");
        node_bundle.addNode("BUNDLE", configIndication);
        tree.addLink(node_bundle);

        NodeLink node_cache_svr_ne = new NodeLink("/Ems/1/Ne/*");
        node_cache_svr_ne.addNode("APP_NR8000_CACHE", configIndication);
        tree.addLink(node_cache_svr_ne);

        //rm
        NodeLink node_Res_svr = new NodeLink("/Ems/1/Ne/*");
        node_Res_svr.addNode("APP_NE_RES", configIndication);
        tree.addLink(node_Res_svr);

        NodeLink phyLinkRes = new NodeLink("/Ems/1/PhysicalLink/*");
        phyLinkRes.addNode("APP_PHY_LINK_MONITOR", configIndication);
        tree.addLink(phyLinkRes);

        // phy_link
        NodeLink topo_server_confSet = new NodeLink("**/Product/*");
        topo_server_confSet.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_confSet);

//        NodeLink topo_server_link = new NodeLink("**/PhysicalLink/*");
//        topo_server_link.addNode("APP_TOPO_SERVER", configIndication);
//        tree.addLink(topo_server_link);

        NodeLink topo_server_neState = new NodeLink("/Ems/1/Ne/*");
        topo_server_neState.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_neState);

        NodeLink topo_server_switchNode = new NodeLink("/Ems/1/SwitchTransNode/*");
        topo_server_switchNode.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_switchNode);

        NodeLink topo_server_mplsNode = new NodeLink("/Ems/1/MplsTransNode/*");
        topo_server_mplsNode.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_mplsNode);

        NodeLink security_client_Node = new NodeLink("/Ems/1/Ne/*");
        security_client_Node.addNode("APP_SECURITY_CLIENT", configIndication);
        tree.addLink(security_client_Node);

        NodeLink security_para = new NodeLink("/Ems/1/Ne/*/Communication/snmpV3");
        security_para.addNode("APP_SECURITY_CLIENT", configIndication);
        tree.addLink(security_para);

        NodeLink node_vm_task = new NodeLink("/Ems/1/VersionModule/1/TaskGroup/*/Task/*");
        node_vm_task.addNode("APP_VM_GUI", configIndication);
        tree.addLink(node_vm_task);

        NodeLink node_vm_subtask = new NodeLink("/Ems/1/VersionModule/1/TaskGroup/*/Task/**");
        node_vm_subtask.addNode("APP_MW_VM", configIndication);
        tree.addLink(node_vm_subtask);


        NodeLink node_vm_svr = new NodeLink("/Ems/1/Ne/*");
        node_vm_svr.addNode("APP_MW_VM", configIndication);
        tree.addLink(node_vm_svr);

        NodeLink phyLinkEvent = new NodeLink("/Ems/1/PhysicalLink/*");
        phyLinkEvent.addNode("APP_NR8000_ALARM", event);
        tree.addLink(phyLinkEvent);

        NodeLink syncNode = new NodeLink("/Ems/1/SyncConfig/*");
        syncNode.addNode("APP_SYNC_SCHEDULER", configIndication);
        tree.addLink(syncNode);


        //pm
        NodeLink node_Pm_svr = new NodeLink("/Ems/1/Ne/*");
        node_Pm_svr.addNode("APP_NE_PM", configIndication);
        tree.addLink(node_Pm_svr);

        NodeLink node_Pm_svr_snmp = new NodeLink("/Ems/1/Ne/*/Communication/snmpV3");
        node_Pm_svr_snmp.addNode("APP_NE_PM", configIndication);
        tree.addLink(node_Pm_svr_snmp);

        NodeLink node_vm_taskgroup = new NodeLink("/Ems/1/VersionModule/1/TaskGroup/*");
        node_vm_taskgroup.addNode("APP_VM_GUI", configIndication);
        tree.addLink(node_vm_taskgroup);
        
        
        //logical view

        NodeLink node_LLDP_phylink = new NodeLink("**/PhysicalLink/*");
        node_LLDP_phylink.addNode("APP_LLDPLogicalLink", configIndication);
        tree.addLink(node_LLDP_phylink);


        NodeLink node_TDM_phylink = new NodeLink("**/PhysicalLink/*");
        node_TDM_phylink.addNode("APP_TDM", configIndication);
        tree.addLink(node_TDM_phylink);

        NodeLink node_LV_ne = new NodeLink("**/Ne/*");
        node_LV_ne.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_ne);

        NodeLink node_LV_SwitchTransNode = new NodeLink("**/SwitchTransNode/*");
        node_LV_SwitchTransNode.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_SwitchTransNode);
        
        NodeLink node_LV_MplsTransNode = new NodeLink("**/MplsTransNode/*");
        node_LV_MplsTransNode.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_MplsTransNode);
        
        NodeLink node_LV_NeGroup = new NodeLink("**/GroupNode/*");
        node_LV_NeGroup.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_NeGroup);

        NodeLink node_LV_phylink = new NodeLink("**/LLDPLogicalLink/*");
        node_LV_phylink.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_phylink);

        NodeLink node_LV_BlockedVlan = new NodeLink("**/BlockedVlan/*");
        node_LV_BlockedVlan.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_LV_BlockedVlan);

        NodeLink node_lv_port = new NodeLink("**/TrafficPort/*");
        node_lv_port.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_lv_port);

        NodeLink node_lv_port_vlan = new NodeLink("**/Vlan");
        node_lv_port_vlan.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_lv_port_vlan);

        NodeLink node_lv_port_vlan_details = new NodeLink("**/Details");
        node_lv_port_vlan_details.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_lv_port_vlan_details);

        NodeLink node_lv_port_vlan_details_ctos = new NodeLink("/Ems/1/Ne/*/Product/1/Shelf/1/TrafficPort/*/Vlan/Details/CToS/*");
        node_lv_port_vlan_details_ctos.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(node_lv_port_vlan_details_ctos);


        NodeLink node_e2e_phylink = new NodeLink("**/PhysicalLink/*");
        node_e2e_phylink.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(node_e2e_phylink);

        //e2e
        NodeLink node_qinq = new NodeLink("/Ems/1/QinQ/*");
        node_qinq.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_qinq);
        //evc
        NodeLink node_evc = new NodeLink("/Ems/1/EVC/*");
        node_evc.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_evc);

        NodeLink node_Ces = new NodeLink("/Ems/1/CesService/*");
        node_Ces.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_Ces);

        NodeLink node_Ces_Server = new NodeLink("/Ems/1/CesService/*");
        node_Ces_Server.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(node_Ces_Server);

        NodeLink node_ne = new NodeLink("/Ems/1/Ne/*");
        node_ne.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_ne);

        NodeLink node_ds = new NodeLink("/Ems/1/DSDomain/*");
        node_ds.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_ds);

        NodeLink node_ne_Server = new NodeLink("/Ems/1/Ne/*");
        node_ne_Server.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(node_ne_Server);


        //license
        NodeLink node_license_svr = new NodeLink("/Ems/1/Ne/*");
        node_license_svr.addNode("APP_NE_LICENSE", configIndication);
        tree.addLink(node_license_svr);


        //topo client
        NodeLink node_topoClient_logicalNode = new NodeLink("**/LogicalNode/*");
        node_topoClient_logicalNode.addNode("APP_TOPO_CLIENT", configIndication);
        tree.addLink(node_topoClient_logicalNode);

        NodeLink node_topoClient_groupNode = new NodeLink("**/GroupNode/*");
        node_topoClient_groupNode.addNode("APP_TOPO_CLIENT", configIndication);
        tree.addLink(node_topoClient_groupNode);

        NodeLink node_topoClient_logicalLink = new NodeLink("**/LogicalLink/*");
        node_topoClient_logicalLink.addNode("APP_TOPO_CLIENT", configIndication);
        tree.addLink(node_topoClient_logicalLink);

        NodeLink node_topoClient_GroupLink = new NodeLink("**/GroupLink/*");
        node_topoClient_GroupLink.addNode("APP_TOPO_CLIENT", configIndication);
        tree.addLink(node_topoClient_GroupLink);

        NodeLink node_cm = new NodeLink("/Ems/1/BatchTaskModule/1/CmTaskGroup/**/CmSubTask/*");
        node_cm.addNode("APP_CM", configIndication);
        node_cm.addNode("APP_CM_CLIENT", configIndication);
        tree.addLink(node_cm);

        NodeLink node_cm_client = new NodeLink("/Ems/1/BatchTaskModule/1/CmTaskGroup/*");
        node_cm_client.addNode("APP_CM_CLIENT", configIndication);
        tree.addLink(node_cm_client);

        NodeLink device = new NodeLink("**/DevicePara/*");
        device.addNode("APP_CM", configIndication);
        tree.addLink(device);

        NodeLink node_cm_attribute = new NodeLink("/Ems/1/Ne/*");
        node_cm_attribute.addNode("APP_CM_CLIENT", configIndication);
        tree.addLink(node_cm_attribute);

        NodeLink node_cm_attribute2 = new NodeLink("/Ems/1/IpV4/*");
        node_cm_attribute2.addNode("APP_CM_CLIENT", configIndication);
        tree.addLink(node_cm_attribute2);

        NodeLink node_mutex_gui = new NodeLink("/Ems/1/CmModule/1/Mutex");
        node_mutex_gui.addNode("APP_CM_CLIENT", event);
        tree.addLink(node_mutex_gui);

        NodeLink switchTrans = new NodeLink("/Ems/1/SwitchTransNode/*");
        switchTrans.addNode("APP_TRANS_NODE_MONITOR", configIndication);
        tree.addLink(switchTrans);

        NodeLink mplsTrans = new NodeLink("/Ems/1/MplsTransNode/*");
        mplsTrans.addNode("APP_TRANS_NODE_MONITOR", configIndication);
        tree.addLink(mplsTrans);

        NodeLink neStateEvent = new NodeLink("/Ems/1/Ne/*");
        neStateEvent.addNode("APP_MW_POLL", action);
        tree.addLink(neStateEvent);

        NodeLink node_poll = new NodeLink("/Ems/1/Ne/*");
        node_poll.addNode("APP_MW_POLL", configIndication);
        tree.addLink(node_poll);

        //im
        NodeLink node_im_svr = new NodeLink("/Ems/1/Ne/*");
        node_im_svr.addNode("APP_IM", configIndication);
        tree.addLink(node_im_svr);

        NodeLink node_im_client = new NodeLink("/Ems/1/Ne/*");
        node_im_client.addNode("APP_IM_GUI", configIndication);
        tree.addLink(node_im_client);

        //siteReport

        NodeLink sitereportSuperGui = new NodeLink("/Ems/1/SiteReportModule/1/SiteReportSuperTask/*");
        sitereportSuperGui.addNode("APP_NEIN_GUI", configIndication);
        tree.addLink(sitereportSuperGui);
        NodeLink sitereportSubGui = new NodeLink("/Ems/1/SiteReportModule/1/SiteReportSuperTask/*/SiteReportSubTask/*");
        sitereportSubGui.addNode("APP_NEIN_GUI", configIndication);
        tree.addLink(sitereportSubGui);

        NodeLink sitereportSuper = new NodeLink("/Ems/1/SiteReportModule/1/SiteReportSuperTask/*");
        sitereportSuper.addNode("APP_SITEREPORT", configIndication);
        tree.addLink(sitereportSuper);

        NodeLink sitereportSub = new NodeLink("/Ems/1/SiteReportModule/1/SiteReportSuperTask/*/SiteReportSubTask/*");
        sitereportSub.addNode("APP_SITEREPORT", configIndication);
        tree.addLink(sitereportSub);

        NodeLink snmpV3 = new NodeLink("**/snmpV3");
        snmpV3.addNode("APP_SECURITY_NE", configIndication);
        snmpV3.addNode("APP_MW_POLL",configIndication);
        tree.addLink(snmpV3);

        NodeLink https = new NodeLink("**/https");
        https.addNode("APP_SECURITY_NE", configIndication);
        tree.addLink(https);


        //Inspection

        NodeLink Inspection_Ne = new NodeLink("/Ems/1/Ne/*");
        Inspection_Ne.addNode("APP_NEIN", configIndication);
        tree.addLink(Inspection_Ne);

        NodeLink Inspection_Ne2 = new NodeLink("/Ems/1/Ne/*");
        Inspection_Ne2.addNode("APP_NEIN_NE", configIndication);
        tree.addLink(Inspection_Ne2);

        NodeLink inventory = new NodeLink("/Ems/1/InventoryModule/1/SyncInventory/*");
        inventory.addNode("APP_IM_GUI", event);
        tree.addLink(inventory);

        NodeLink sitereportInventoryrespense = new NodeLink("/Ems/1/InventoryModule/1/SyncInventory/*");
        inventory.addNode("APP_SITEREPORT", event);
        tree.addLink(sitereportInventoryrespense);

        NodeLink neLog_Ne = new NodeLink("/Ems/1/Ne/*");
        neLog_Ne.addNode("APP_NE_LOG", configIndication);
        tree.addLink(neLog_Ne);

        NodeLink node_vm_package = new NodeLink("/Ems/1/VersionModule/1/UnifiedVersionPackage/**");
        node_vm_package.addNode("APP_VM_GUI", configIndication);
        tree.addLink(node_vm_package);

        getInstance(SmartLinkRepository.class).add("MOS", tree);
    }
}