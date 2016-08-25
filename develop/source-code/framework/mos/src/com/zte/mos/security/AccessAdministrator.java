package com.zte.mos.security;

import java.util.HashSet;

/**
 * Created by luoqingkai on 14-10-18.
 */
public class AccessAdministrator
{

    private static HashSet<UserKey> legalUserKeys = new HashSet<UserKey>();

    public static boolean check(UserKey userKey)
    {
        return true;
    }
}
