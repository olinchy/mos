package com.zte.mos.app;

import com.zte.mos.exception.MOSException;
import com.zte.mos.im.model.NR8000InventoryModel;
import com.zte.mos.inventory.cache.service.InventoryCacheDbService;
import com.zte.smartlink.SmartLinkNode;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shz on 16-3-2.
 */
public class SiteReportStub extends SmartLinkNode
{
    @Override
    public Remote getService() throws MOSException, RemoteException
    {
        return new Remote()
        {
            @Override
            public int hashCode()
            {
                return super.hashCode();
            }
        };
    }

    @Override
    public String getName()
    {
        return "sitereport_verify_stub";
    }

    @Override
    public void post() throws MOSException
    {
        InventoryCacheDbService inventoryCacheDbService = InventoryCacheDbService.getInstance();
//        NR8000InventoryModel nr8000InventoryModel = new NR8000InventoryModel();
//        nr8000InventoryModel.setParentOID("mw.nr8960=2");
//        nr8000InventoryModel.setOID("mw.nr8960=2,slot=1,boardType=RTUB");
//        NR8000InventoryModel nr8000InventoryModel2 = new NR8000InventoryModel();
//        nr8000InventoryModel2.setParentOID("mw.nr8960=3");
//        nr8000InventoryModel2.setOID("mw.nr8960=3,slot=1,boardType=RTUB");
//        inventoryCacheDbService.addOrSet(nr8000InventoryModel2);

        List<NR8000InventoryModel> listmodel = new ArrayList<NR8000InventoryModel>();
        NR8000InventoryModel inventoryModel = new NR8000InventoryModel();
        String oid = "mw.nr8960=1";
        inventoryModel.setOID(oid + ",slot=1,boardType=RCUC");
        inventoryModel.setBoardType("RCUB");
        inventoryModel.setBootVersion("V14.11.20");
        inventoryModel.setEPLDVersion("1");
        inventoryModel.setNEVersion("NR8000V2.04.02.12B03");
        inventoryModel.setPosition("1.1.1");
        inventoryModel.setSequenceNO("261732400011");
        inventoryModel.setParentOID(oid);
        listmodel.add(inventoryModel);

        NR8000InventoryModel inventoryModel2 = new NR8000InventoryModel();
        String oid2 = "mw.nr8960=1";
        inventoryModel2.setOID(oid + ",slot=2,boardType=RMUM");
        inventoryModel2.setBoardType("RCUB");
        inventoryModel2.setBootVersion("V14.11.20");
        inventoryModel2.setEPLDVersion("1");
        inventoryModel2.setNEVersion("NR8000V2.04.02.12B03");
        inventoryModel2.setPosition("1.1.1");
        inventoryModel2.setSequenceNO("261732400011");
        inventoryModel2.setParentOID(oid2);
        listmodel.add(inventoryModel2);

        inventoryCacheDbService.adds(listmodel);
    }

    @Override
    public String depends()
    {
        return "APP_IM";
    }
}
