package com.mos.lite.http_service;

/**
 * Created by olinchy on 15-12-28.
 */
public enum Role implements UserCreater
{
    EMS
            {
                @Override
                public User create()
                {
                    return new EMS();
                }
            },
    CLI
            {
                @Override
                public User create()
                {
                    return new CLI();
                }
            },
    MOSSERVICE
    {
        @Override
        public User create()
        {
            return new CLI();
        }
    }
}
