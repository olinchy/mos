package com.zte.mos.task.domain;

/**
 * Created by luoqingkai on 15-10-27.
 */
public abstract class AbstractStep implements Runnable {
    protected abstract AbstractStep next();

}
