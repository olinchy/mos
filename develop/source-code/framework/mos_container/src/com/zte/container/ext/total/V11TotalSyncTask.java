package com.zte.container.ext.total;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.domain.ModelHead;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.LockedByTransException;
import com.zte.mos.exception.MOSException;
import com.zte.mos.msg.framework.DeletedMo;
import com.zte.mos.msg.framework.operation.ReverseSynResponse;
import com.zte.mos.type.Pair;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.JsonUtil;

import java.util.ArrayList;
import java.util.UUID;

import static com.zte.mos.util.basic.Logger.logger;
import static com.zte.container.ext.total.ReverseSyncResult.failed;
import static com.zte.container.ext.total.ReverseSyncResult.success;
import static com.zte.container.ext.total.ReverseSyncResult.syncConflict;

/**
 * Created by luoqingkai on 3/7/16.
 * NR8960 V242 only
 */
public class V11TotalSyncTask extends AbstractTotalSyncTask
{

    private static Logger log = logger(V11TotalSyncTask.class);
    private int sn;
    private JsonNode moNodeList;
    private TargetAddress target;
    private UUID key = null;

    public V11TotalSyncTask(TargetAddress target, JsonNode moList, int sn, Pair<String, Object>[] paras)
    {
        super(target.getTargetID(), paras);
        this.target = target;
        this.moNodeList = moList;
        this.sn = sn;
    }

    @Override
    public void run()
    {
        ReverseSynResponse response = null;
        ImagedSystem sys = target.getSystem();
        ReverseSyncResult syncOK = failed;
        try
        {
            response = new ReverseSynResponse(0, sn, parseToMoArray(), null);
        }
        catch (MOSException e)
        {
            log.error(e.getMessage() + ": parse error " + target.getTargetID(), e);
            doAfterSyncing(sys, syncOK);
            return;
        }

        try
        {
            this.key = sys.lock();
            syncOK = sys.sync(response, key)?success:failed;
            log.debug(target.getTargetID() + " reverse config done!");
        }
        catch (LockedByTransException e)
        {
            log.error(e.getMessage() + ": " + target.getTargetID(), e);
            syncOK = syncConflict;
        }
        catch (MOSException e)
        {
            log.error("reverse save fail:" + target.getTargetID(), e);
        }
        catch (Exception e)
        {
            log.error("unknown error:" + target.getTargetID(), e);
        }
        finally
        {
            doAfterSyncing(sys, syncOK);
        }

    }

    private void doAfterSyncing(ImagedSystem system, ReverseSyncResult result)
    {
        releaseLock(system);
        sendEvent(result);
//        sendEvent(result ? ReverseSyncResult.success : ReverseSyncResult.failed);
    }

    private void releaseLock(ImagedSystem sys)
    {
        sys.unlock(key);
        log.debug("system key unlock:" + target.getTargetID());
    }

    private ManagementObject[] parseToMoArray() throws MOSException
    {
        ModelHead head = target.getModelHead();
        ArrayNode array = (ArrayNode)moNodeList;
        ArrayList<ManagementObject> moArray = new ArrayList<ManagementObject>();
        //ManagementObject[] moArray = new ManagementObject[array.size()];

        for (int i = 0; i < array.size(); i++){
            JsonNode oneNode = array.get(i);
            String dnStr = oneNode.get("dn").asText();
            JsonNode moNode = oneNode.get("mo");

            if (moNode != null){
                String moClazz = oneNode.get("moClass").asText();
                String moStr = JsonUtil.toString(moNode);
                ManagementObject mo = head.buildMo(moClazz, moStr);
                if (mo != null){
                    mo.setDn(dnStr);
                    moArray.add(mo);
                }

            }else{
                log.debug(target.toString() + " delete mo " + dnStr);
                moArray.add(new DeletedMo(dnStr));
            }
        }
        return moArray.toArray(new ManagementObject[moArray.size()]);
    }

    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o))
        {
            return false;
        }
        return moNodeList.equals(((V11TotalSyncTask) o).moNodeList);
    }

}
