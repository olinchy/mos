package com.zte.mos.msg.framework.operation;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;

/**
 * Created by luoqingkai on 15-5-26.
 */
public class GetResponse extends AbstractResponse {
    protected Mo mo;

    public GetResponse(int result, Mo mo) {
        super(result);
        this.mo = mo;
    }


    public Mo getMo() throws MOSException {
        return mo;
    }
}
