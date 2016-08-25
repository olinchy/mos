package com.zte.mos.app.switchmodel.service;

import java.util.ArrayList;
import java.util.List;
import static com.zte.mos.util.Singleton.*;
/**
 * Created by ccy on 5/27/16.
 */
public class ModelSwitchEventMonitor implements Observable
{
    private List<Observer> list = new ArrayList<Observer>();

    private ModelSwitchEventMonitor()
    {

    }

    @Override
    public void attach(Observer observer)
    {
        list.clear();
        list.add(observer);
    }

    @Override
    public void detach(Observer observer)
    {
        list.remove(observer);
    }

    @Override
    public void notifyObservers(String result)
    {
        for(Observer ob : list)
        {
            ob.update(result);
        }
    }

    public static void notifyAllObservers(String result)
    {
        try
        {
            getInstance(ModelSwitchEventMonitor.class).notifyObservers(result);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }

    public static void registerObserver(Observer observer)
    {
        try
        {
            getInstance(ModelSwitchEventMonitor.class).attach(observer);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
