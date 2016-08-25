package com.zte.mos.app.switchmodel.service;

/**
 * Created by ccy on 5/27/16.
 */
public interface Observable
{
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(String result);
}
