package com.mos.lite.http_service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by olinchy on 15-11-18.
 */
public class LoginUserStore
{
    private LoginUserStore()
    {
    }

    private ConcurrentHashMap<String, User> map = new ConcurrentHashMap<String, User>();

    public User get(String sessionId)
    {
        return map.get(sessionId);
    }

    public void remove(String sessionId)
    {
        map.remove(sessionId);
    }

    public void add(String sessionId, User user)
    {
        map.put(sessionId, user);
    }
}
