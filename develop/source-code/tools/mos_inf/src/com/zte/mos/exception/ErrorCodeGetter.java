package com.zte.mos.exception;

/**
 * Created by olinchy on 15-5-20.
 */
public class ErrorCodeGetter
{
    public static long getErrorCode(String keyword)
    {
        return 1;
    }

    public static final int SAVING_TO_DATABASE_ERROR = 944123;
    public static final int COMMUNICATION_ERROR = 945215;

    public static final int ACK_ERROR = 958101;
    public static final int BATCH_ERROR = 958102;
    public static final int DELETE_GROUP_ERROR = 958103;
    public static final int EMPTY_REQUEST_ERROR = 958104;
    public static final int EXISTED_MODEL_SWiTCH_TRANSACTION_ERROR = 958105;
    public static final int LOCKED_BY_TRANS_ERROR = 958106;
    public static final int LOGIN_ERROR = 958107;
    public static final int MO_TO_RECORD_ERROR = 958108;
    public static final int MODEL_NOT_SPEC_ERROR = 958109;
    public static final int RMI_ERROR = 958110;
    public static final int NONVALID_KEYWORD_ERROR = 958111;
    public static final int NO_ROUTE_PATH_ERROR = 958112;
    public static final int NO_TARGET_ERROR = 958113;
    public static final int NO_TRANSACTION_ALLOWED_IN_REFERNCE_DB_ERROR = 958114;
    public static final int NULL_MO_ERROR = 958115;
    public static final int PERSISTENT_ERROR = 958116;
    public static final int READ_PERSISTENT_ERROR = 958117;
    public static final int REMOTE_OPER_ERROR = 958118;
    public static final int SWITCH_MODEL_ERROR = 958119;
}
