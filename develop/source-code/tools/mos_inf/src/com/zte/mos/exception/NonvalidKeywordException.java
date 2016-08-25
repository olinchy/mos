package com.zte.mos.exception;

/**
 * Created by olinchy on 6/6/14 for MO_JAVA.
 */
public class NonvalidKeywordException extends MOSException
{
    public NonvalidKeywordException() {
        super(new ErrorCode(ErrorCodeGetter.NONVALID_KEYWORD_ERROR), NonvalidKeywordException.class.getName());
    }
}
