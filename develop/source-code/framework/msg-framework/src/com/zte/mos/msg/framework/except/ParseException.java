package com.zte.mos.msg.framework.except;

/**
 * Created by luoqingkai on 15-9-24.
 */
public class ParseException extends MsgFrameException{
    public ParseException() {
        setErrorCode(958005);
    }
}
