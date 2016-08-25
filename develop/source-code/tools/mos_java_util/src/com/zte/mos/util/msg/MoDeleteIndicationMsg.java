/**
 * </pre>
 *
 * @version 1.0
 * @author l10121897
 */
package com.zte.mos.util.msg;

import com.zte.mos.message.Mo;

import static com.zte.mos.inf.MoCmds.MoDeleteInd;

/**
 * @author l10121897
 * @desc
 * @date 2014-4-21
 */
@SuppressWarnings("serial")
public class MoDeleteIndicationMsg extends IndicateMsg
{
    private final Mo oldValue;

    public MoDeleteIndicationMsg(String dn, Mo mo)
    {
        super(MoDeleteInd, dn);
        this.oldValue = mo;
    }

    @Override
    public Mo oldValue()
    {
        return oldValue;
    }

    @Override
    public Mo newValue()
    {
        return null;
    }
}
