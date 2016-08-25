package com.zte.mos.msg.framework.observer;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by ccy on 3/4/16.
 */
public class DefaultObserver implements Observer
{
    public static DefaultObserver dummy = new DefaultObserver();
    @Override
    public void update(Object arg)
    {
        logger(DefaultObserver.class).info("notify observer " + arg);
    }

    private DefaultObserver()
    {

    }
}
