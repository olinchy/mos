package com.services;


public final class AppService
{
    private static LocalNeService localNeService = null;

    public static void publish(LocalNeService sv)
    {
        localNeService = sv;
    }
    public static LocalNeService getLocalNeService()
    {
        return localNeService;
    }
}
