package com.zte.mos.util.basic;

import com.zte.ums.uep.api.util.DebugPrn;

public class Logger
{
    private final DebugPrn logger;

    private Logger(String name)
    {
        this.logger = new DebugPrn(name);
    }

    public static Logger logger(Class<?> clazz)
    {
        return new Logger(clazz.getName());
    }

    public static Logger logger(String name)
    {
        return new Logger(name);
    }

    public void info(String msg)
    {
        logger.info(formatMsg(msg));
    }

    public void info(String msg, Throwable e)
    {
        logger.info(formatMsg(msg), e);
    }

    public void info(Object message)
    {
        logger.info(formatMsg(message));
    }

    public void error(Object message, Throwable t) {
        logger.error(formatMsg(message), t);
    }

    public void error(String msg)
    {
        logger.error(formatMsg(msg));
    }

    private String formatMsg(Object msg)
    {

        return String.format("[thr: %1$s] (%2$s) %3$s", Thread.currentThread().getName(), new Throwable().getStackTrace()[2].toString(), msg);
    }

    public void error(String msg, Throwable e)
    {
        logger.error(formatMsg(msg), e);
    }

    public void warn(String msg)
    {
        logger.warn(formatMsg(msg));
    }

    public void warn(String msg, Throwable e)
    {
        logger.warn(formatMsg(msg), e);
    }

    public void debug(String msg, Throwable e)
    {
        logger.info(formatMsg(msg), e);
    }

    public void debug(String msg)
    {
        logger.info(formatMsg(msg));
    }

    public boolean isDebugEnabled()
    {
        return logger.isDebugEnabled();
    }

    public void test(String s)
    {
    }

}
