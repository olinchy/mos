package com.zte.container.ext.diff;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.domain.Diff;
import com.zte.mos.domain.ImagedSystem;
import com.zte.mos.domain.OperationLog;
import com.zte.mos.domain.TargetAddress;
import com.zte.mos.exception.MOSException;
import com.zte.mos.task.SchedulingTask;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.tools.JsonUtil;

import java.util.LinkedList;

import static com.zte.mos.util.basic.Logger.logger;


public class DefaultSyncDiffTask extends SchedulingTask
{
    private static Logger log = logger(DefaultSyncDiffTask.class);
    private final TargetAddress address;
    private JsonNode node;

    public DefaultSyncDiffTask(TargetAddress address, JsonNode node)
    {
        this.address = address;
        this.node = node;
    }

    @Override
    public void run()
    {
        ImagedSystem sys = address.getSystem();

        int revision = node.get("revision").asInt();
        if (revision <= address.getSystem().revision())
        {
            return;
        }

        try
        {
            LinkedList<OperationLog> operList = parse();
            Diff diff = new DiffImpl(revision, operList);
            sys.syncDiff(diff);
        }
        catch (MOSException e)
        {
            log.warn(this.address.getTargetID() + " sync diff parse error:", e);
        }
    }

    private LinkedList<OperationLog> parse() throws MOSException
    {
        ArrayNode arrayNode = (ArrayNode) node.get("moOpList");
        LinkedList<OperationLog> list = new LinkedList<OperationLog>();

        for (JsonNode oneNode : arrayNode)
        {
            String moClass = null;
            String type = oneNode.get("type").asText();
            String dn = oneNode.get("dn").asText();
            if (!type.equals("del"))
            {
                moClass = oneNode.get("moClass").asText();
            }

            JsonNode moNode = oneNode.get("mo");
            String mo = JsonUtil.toString(moNode);

            OperationLog unit = new OperationLog(type, dn, moClass, mo);
            list.add(unit);
        }
        return list;
    }

    @Override
    protected String identity()
    {
        return address.getTargetID();
    }

    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o))
        {
            return false;
        }
        return node.equals(((DefaultSyncDiffTask) o).node);
    }
}
