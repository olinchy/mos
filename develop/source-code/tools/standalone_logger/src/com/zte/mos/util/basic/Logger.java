package com.zte.mos.util.basic;

public class Logger
{
    private Logger(Class<?> name)
    {
        this.logger = org.apache.log4j.Logger.getLogger(name);
    }

    private final org.apache.log4j.Logger logger;

    public static Logger logger(Class<?> clazz)
    {
        return new Logger(clazz);
    }

    public void info(String string)
    {
        logger.info(string);
    }

    public void info(String msg, Throwable e)
    {
        logger.info(msg, e);
    }

    public void info(Object message)
    {
        logger.info(message);
    }

    public void error(Object message, Throwable t) {
        logger.error(message, t);
    }

    public void error(String string, Throwable e)
    {
        try
        {
            logger.error(string, e);
        }
        catch (Throwable e1)
        {
            logger.error(string);
        }
    }

    public void error(String msg){
        logger.error(msg);
    }

    public void warn(String string, Throwable e)
    {
        try
        {
            logger.warn(string, e);
        }
        catch (Throwable e1)
        {
            logger.warn(string);
        }
    }

    public void warn(String string)
    {
        try
        {
            logger.warn(string);
        }
        catch (Throwable e1)
        {
            logger.warn(string);
        }
    }

    public void debug(String msg, Throwable e)
    {
        try
        {
            logger.debug(msg, e);
        }
        catch (Throwable e1)
        {
            logger.debug(msg);
        }
    }

    public void debug(String msg)
    {
        logger.debug(msg);
    }

    public void test(String s)
    {
        logger.debug(s);
    }

    public boolean isDebugEnabled(){
        return logger.isDebugEnabled();
    }
}
