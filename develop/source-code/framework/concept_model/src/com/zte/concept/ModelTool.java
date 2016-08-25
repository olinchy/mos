package com.zte.concept;

import com.zte.mos.inf.MoCmds;

/**
 * Created by luoqingkai on 15-8-18.
 */
public class ModelTool {
    public static String buildKey(String mib, MoCmds cmd){
        return mib + "_" + cmd.name();
    }
}
