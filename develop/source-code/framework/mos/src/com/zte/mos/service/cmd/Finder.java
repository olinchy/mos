package com.zte.mos.service.cmd;

import com.zte.mos.domain.DN;
import com.zte.mos.domain.ManagementObject;
import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.service.MOS;
import com.zte.mos.storage.triggers.MoWalker;
import com.zte.mos.tools.Expression;
import com.zte.mos.util.msg.MoFindMsg;

import java.util.ArrayList;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 3/20/15 for mosjava.
 */
public class Finder
{
    private final Expression expression;
    private final MOS mos;
    private final MoFindMsg findMsg;

    public Finder(Expression expression, MOS mos, MoFindMsg findMsg)
    {
        this.expression = expression;
        this.mos = mos;
        this.findMsg = findMsg;
    }

    public int find(final ArrayList<Mo> moes) throws MOSException
    {
        MoWalker walker = new MoWalker(findMsg.getDnRegex(), new DN(findMsg.dn()), expression, mos);

        int size = 0;
        if (walker.isLocal())
        {
            mos.all(walker, findMsg.getTransactionID());
            ArrayList<ManagementObject> collection = walker.getLocal();
            size = collection.size();

            int defaultEndIndex = size - 1;

            if (findMsg.getStartIndex() <= defaultEndIndex)
            {
                int endIndex = defaultEndIndex;
                if (findMsg.getExpectedCount() != -1 && (findMsg.getStartIndex() + findMsg.getExpectedCount() - 1) <= defaultEndIndex)
                {
                    endIndex = findMsg.getStartIndex() + findMsg.getExpectedCount() - 1;
                }

                logger(this.getClass()).debug(
                        "find from " + findMsg.getStartIndex() + " to " + endIndex + " in " + size + " records");

                for (int i = findMsg.getStartIndex(); i <= endIndex; i++)
                {
                    ManagementObject mo = collection.get(i);
                    Mo moClass = mo.toMoClass().setDn(mo.dn());
                    moes.add(moClass);
                }
            }
            else
            {
                logger(this.getClass()).debug(
                        "find from " + findMsg.getStartIndex() + " is out of range, result set is " + size);
            }
        }

        return size;
    }
}
