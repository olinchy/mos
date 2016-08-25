package com.zte.app.smartlink.mos;

import com.zte.mos.annotation.PreLoad;
import com.zte.smartlink.NodeLink;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;

import static com.zte.mos.util.OperationMask.*;
import static com.zte.mos.util.Singleton.getInstance;

@PreLoad
public class BundleTreeRegister {
    private final SmartLink tree = new SmartLink();

    public BundleTreeRegister() throws Exception {
        NodeLink node_ne_event = new NodeLink("/Ems/1/Ne/*");
        node_ne_event.addNode("MOS_NE_STATE", event);
        tree.addLink(node_ne_event);

        NodeLink node_Ne_action = new NodeLink("/Ems/1/Ne/*");
        node_Ne_action.addNode("MOS", action);
        tree.addLink(node_Ne_action);

        NodeLink node_version_action = new NodeLink("/Ems/1/VersionModule/*");
        node_version_action.addNode("MOS", action);
        tree.addLink(node_version_action);

        NodeLink reference = new NodeLink("/**");
        reference.addNode("MOS", ref);
        tree.addLink(reference);

        NodeLink node_Cm_svr = new NodeLink("/Ems/1/Ne/*/Product/1/SysPara/1/DevicePara/*");
        node_Cm_svr.addNode("APP_CM", configIndication);
        tree.addLink(node_Cm_svr);

        NodeLink node_RpcState_Poll = new NodeLink("/Ems/1/Ne/*/Product/1");
        node_RpcState_Poll.addNode("APP_MW_POLL", event);
        tree.addLink(node_RpcState_Poll);

        NodeLink node_TimeZone = new NodeLink("/Ems/1/Ne/*/Product/1/SysPara/1/Time/1/Sntp/*");
        node_TimeZone.addNode("APP_NR8000_CACHE", configIndication);
        tree.addLink(node_TimeZone);

        NodeLink node_SummerTime = new NodeLink("/Ems/1/Ne/*/Product/1/SysPara/1/Time/1/SummerTime/*");
        node_SummerTime.addNode("APP_NR8000_CACHE", configIndication);
        tree.addLink(node_SummerTime);


        //rm
        //board
        NodeLink board_Res_svr = new NodeLink("**/Board/**");
        board_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(board_Res_svr);
        //Tu
        NodeLink tu_Res_svr = new NodeLink("**/TrafficUnit/**");
        tu_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(tu_Res_svr);
        //PmpVlan
        NodeLink pmpVlan_Res_svr = new NodeLink("**/Pmp/1/Vlan/*");
        pmpVlan_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(pmpVlan_Res_svr);
        //PmpDscp
        NodeLink pmpDscp_Res_svr = new NodeLink("**/Pmp/1/DSCP/*");
        pmpDscp_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(pmpDscp_Res_svr);
        //PmpVlanPri
        NodeLink pmpVlanPri_Res_svr = new NodeLink("**/Pmp/1/VlanPri/*");
        pmpVlanPri_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(pmpVlanPri_Res_svr);
        //PmpHqos
        NodeLink pmpHqos_Res_svr = new NodeLink("**/Pmp/1/HQOS/*");
        pmpHqos_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(pmpHqos_Res_svr);
        //PLA for Rmon and WE
        NodeLink pla_Res_svr = new NodeLink("**/Shelf/*/PLA/*");
        pla_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(pla_Res_svr);
        //TDMCascade
        NodeLink tdmCasc_Res_svr = new NodeLink("**/TdmCascade/*/Port/*");
        tdmCasc_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(tdmCasc_Res_svr);
        //Ces
        NodeLink ces_Res_svr = new NodeLink("**/CesRoute/*");
        ces_Res_svr.addNode("APP_BOARD_RES", configIndication);
        tree.addLink(ces_Res_svr);
//        //port
//        NodeLink port_Res_svr = new NodeLink("**/Board/*/*/*");
//        port_Res_svr.addNode("APP_PORT_RES", configIndication);
//        tree.addLink(port_Res_svr);
//        //ETHPort
//        //ETHPort dn is /Shelf/1/TrafficUnit/0/EthPort/E-1
//        NodeLink ethport_Res_svr = new NodeLink("**/TrafficUnit/*/EthPort/*");
//        ethport_Res_svr.addNode("APP_PORT_RES", configIndication);
//        tree.addLink(ethport_Res_svr);
//        //AirPort of Board
//        NodeLink airport_Res_svr = new NodeLink("**/Board/*/AirPort/*");
//        airport_Res_svr.addNode("APP_PORT_RES", configIndication);
//        tree.addLink(airport_Res_svr);
//        //PM
//        //pm mo of port and airport
//        NodeLink pm_Port_Res_svr = new NodeLink("**/Board/*/*/*/*/*");
//        pm_Port_Res_svr.addNode("APP_PM_PORT_RES", configIndication);
//        tree.addLink(pm_Port_Res_svr);
//        //pm mo of ETHPort
//        NodeLink pm_EthPort_Res_svr = new NodeLink("**/TrafficUnit/*/EthPort/*/*/*");
//        pm_EthPort_Res_svr.addNode("APP_PM_ETH_PORT_RES", configIndication);
//        tree.addLink(pm_EthPort_Res_svr);
//        //pm mo of LogicAirPort
//        NodeLink pm_LogicAirPort_Res_svr = new NodeLink("**/TrafficUnit/*/LogicAirPort/*/PmpRadioLink/*");
//        pm_LogicAirPort_Res_svr.addNode("APP_PM_RADIO_LINK_RES", configIndication);
//        tree.addLink(pm_LogicAirPort_Res_svr);
//
//        //tu
//        NodeLink tu_Res_svr = new NodeLink("**/TrafficUnit/*");
//        tu_Res_svr.addNode("APP_TRAFFIC_UNIT_RES", configIndication);
//        tree.addLink(tu_Res_svr);

//        NodeLink topo_server_board = new NodeLink("**/Board/*");
//        topo_server_board.addNode("APP_TOPO_SERVER", configIndication);
//        tree.addLink(topo_server_board);

        NodeLink topo_server_e1 = new NodeLink("**/Board/*/E1Port/*");
        topo_server_e1.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_e1);

        NodeLink topo_server_stm1 = new NodeLink("**/Board/*/Stm155Port/*");
        topo_server_stm1.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_stm1);

        NodeLink topo_server_stm4 = new NodeLink("**/Board/*/Stm622Port/*");
        topo_server_stm4.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_stm4);

        NodeLink topo_server_radio = new NodeLink("**/Board/*/AirPort/*");
        topo_server_radio.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_radio);

        NodeLink tdmCasc_port = new NodeLink("**/TdmCascade/*/Port/*");
        tdmCasc_port.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(tdmCasc_port);

        NodeLink tdmCasc = new NodeLink("**/TdmCascade/*");
        tdmCasc.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(tdmCasc);

        NodeLink topo_server_maouradio = new NodeLink("**/Board/*/MAouAirPort/*");
        topo_server_maouradio.addNode("APP_TOPO_SERVER", configIndication);
        tree.addLink(topo_server_maouradio);

        //TDM
        NodeLink tdm = new NodeLink("**/TrafficUnit/*");
        tdm.addNode("APP_TDM", configIndication);
        tree.addLink(tdm);

        NodeLink tdm1 = new NodeLink("**/VE1Port/*");
        tdm1.addNode("APP_TDM", configIndication);
        tree.addLink(tdm1);

        NodeLink tdm4 = new NodeLink("**/VStm155Port/*");
        tdm4.addNode("APP_TDM", configIndication);
        tree.addLink(tdm4);

//        NodeLink tdm5 = new NodeLink("**/Stm622Port/*");
//        tdm5.addNode("APP_TDM", configIndication);
//        tree.addLink(tdm5);

        NodeLink tdm6 = new NodeLink("**/LogicAirPort/*");
        tdm6.addNode("APP_TDM", configIndication);
        tree.addLink(tdm6);

        NodeLink tdm2 = new NodeLink("**/P2pRoute/*");
        tdm2.addNode("APP_TDM", configIndication);
        tree.addLink(tdm2);

        NodeLink tdm3 = new NodeLink("**/SncpRoute/*");
        tdm3.addNode("APP_TDM", configIndication);
        tree.addLink(tdm3);


        NodeLink tdm7 = new NodeLink("/Ems/1/Ne/*/Product/1");
        tdm7.addNode("APP_TDM", event);
        tree.addLink(tdm7);

        NodeLink tdm8 = new NodeLink("**/SDHRTBL/*");
        tdm8.addNode("APP_TDM", configIndication);
        tree.addLink(tdm8);

        NodeLink tdm9 = new NodeLink("**/PDHRTBL/*");
        tdm9.addNode("APP_TDM", configIndication);
        tree.addLink(tdm9);

        NodeLink tdm10 = new NodeLink("**/PDHSNCPCFG/*");
        tdm10.addNode("APP_TDM", configIndication);
        tree.addLink(tdm10);
        //LV
        NodeLink lv_server_radio = new NodeLink("**/TrafficPort/*");
        lv_server_radio.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(lv_server_radio);

        NodeLink lv_server_1 = new NodeLink("**/ChipPort/*");
        lv_server_1.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(lv_server_1);

        NodeLink lv_server_port_vlan = new NodeLink("**/Vlan");
        lv_server_port_vlan.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(lv_server_port_vlan);

        NodeLink lv_server_port_details = new NodeLink("**/Details");
        lv_server_port_details.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(lv_server_port_details);

        NodeLink lv_server_ctos = new NodeLink("/Ems/1/Ne/*/Product/1/Shelf/1/TrafficPort/*/Vlan/Details/CToS/*");
        lv_server_ctos.addNode("APP_LV_SERVER", configIndication);
        tree.addLink(lv_server_ctos);
        //LV

        //E2E
        NodeLink app_e2e_vlan = new NodeLink("**/LogicalPort/*");
        lv_server_radio.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(app_e2e_vlan);

        NodeLink node_Ces = new NodeLink("/Ems/1/Ne/*/Product/1/CesRoute/*");
        node_Ces.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_Ces);

        NodeLink node_Ces_Server = new NodeLink("/Ems/1/Ne/*/Product/1/CesRoute/*");
        node_Ces_Server.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(node_Ces_Server);

        NodeLink node_ne = new NodeLink("/Ems/1/Ne/*");
        node_ne.addNode("APP_E2E_GUI", configIndication);
        tree.addLink(node_ne);

        NodeLink node_ne_Server = new NodeLink("/Ems/1/Ne/*");
        node_ne_Server.addNode("APP_E2E_SERVER", configIndication);
        tree.addLink(node_ne_Server);

        NodeLink e2e_server = new NodeLink("/Ems/1/Ne/*/Product/1");
        e2e_server.addNode("APP_E2E_SERVER", event);
        tree.addLink(e2e_server);


        NodeLink reverse_result_notification = new NodeLink("/Ems/1/Ne/*/Product/1");
        reverse_result_notification.addNode("APP_CM_CLIENT", event);
        tree.addLink(reverse_result_notification);

        NodeLink reverse_result_noti = new NodeLink("/Ems/1/Ne/*/Product/1");
        reverse_result_noti.addNode("APP_CM", event);
        reverse_result_noti.addNode("APP_CM_NE", event);
//        reverse_result_noti.addNode("APP_NR8000_ALARM", event);
        reverse_result_noti.addNode("APP_SITEREPORT", event);
        reverse_result_noti.addNode("APP_TOPO_SERVER", event);
        tree.addLink(reverse_result_noti);

        //E2E



//        NodeLink app_vm_ne_downloadresult = new NodeLink("/Ems/1/Ne/*/Product/1/Vmp/1/DownloadResult/1");
//        app_vm_ne_downloadresult.addNode("APP_MW_VM_NE", configIndication);
//        tree.addLink(app_vm_ne_downloadresult);


        NodeLink Inspection_Nedata = new NodeLink("/Ems/1/Ne/*/Product/1/**");
        Inspection_Nedata.addNode("APP_NEIN", configIndication);
        tree.addLink(Inspection_Nedata);

        NodeLink model_switch_link = new NodeLink("/Ems/1/ModelSwitchEnd/1");
        model_switch_link.addNode("SIMU_MODEL_SWITCH_APP", event);
        tree.addLink(model_switch_link);

        NodeLink mosStateChange = new NodeLink("MosStateChangeEvent");
        mosStateChange.addNode("APP_NR8000_CACHE", event);
        tree.addLink(mosStateChange);




        getInstance(SmartLinkRepository.class).add("BUNDLE", tree);
    }
}