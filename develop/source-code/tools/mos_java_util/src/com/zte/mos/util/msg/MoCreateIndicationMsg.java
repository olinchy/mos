/**
 * </pre>
 *
 * @version 1.0
 * @author l10121897
 */
package com.zte.mos.util.msg;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;

import java.io.IOException;

import static com.zte.mos.inf.MoCmds.MoCreateInd;

/**
 * @author l10121897
 * @desc
 * @date 2014-4-21
 */
@SuppressWarnings("serial")
public class MoCreateIndicationMsg extends IndicateMsg
{
    private final Mo newValue;

    public MoCreateIndicationMsg(String dn, Mo mo) throws MOSException
    {
        super(MoCreateInd, dn);
        this.newValue = mo;
    }

    @Override
    public Mo oldValue()
    {
        return null;
    }

    @Override
    public Mo newValue()
    {
        return newValue;
    }
}
