package com.zte.mos.util.jettylog;

import java.util.logging.Level;
import org.eclipse.jetty.util.log.AbstractLogger;
import org.eclipse.jetty.util.log.Logger;

public class JettyUepLogger extends AbstractLogger
{
    private static com.zte.mos.util.basic.Logger logger = null;
    private String name = null;

    public JettyUepLogger()
    {
        this(JettyUepLogger.class.getName());
    }
    
    public JettyUepLogger(String name)
    {
        this.name = name;
        logger = com.zte.mos.util.basic.Logger.logger(this.name);
    }

    public String getName()
    {
        return name;
    }

    public void warn(String msg, Object... args)
    {
        logger.warn(format(msg,args));
    }

    public void warn(Throwable thrown)
    {
        warn("", thrown);
    }

    public void warn(String msg, Throwable thrown)
    {
        logger.warn(msg, thrown);
    }

    public void info(String msg, Object... args)
    {
        logger.info(format(msg, args));
    }

    public void info(Throwable thrown)
    {
        info("", thrown);
    }

    public void info(String msg, Throwable thrown)
    {
        logger.info(msg, thrown);
    }

    public boolean isDebugEnabled()
    {
        return logger.isDebugEnabled();
    }

    public void setDebugEnabled(boolean enabled)
    {
        if (enabled)
        {
            throw new UnsupportedOperationException("not supported setDebugEnabled(boolean enabled)");
        }
    }

    protected Logger newLogger(String fullname)
    {
        //throw new UnsupportedOperationException("not supported newLogger(String fullname)");
        return new JettyUepLogger(fullname);
    }
    
    public void debug(String msg, Object... args)
    {
        logger.debug(format(msg, args));
    }

    public void debug(String msg, long arg)
    {
        logger.debug(format(msg, arg));
    }

    public void debug(Throwable thrown)
    {
        debug("", thrown);
    }

    public void debug(String msg, Throwable thrown)
    {
        logger.debug(msg, thrown);
    }
    
    public void ignore(Throwable ignored)
    {
        debug(ignored);
    }
    
    private String format(String msg, Object... args)
    {
        msg = String.valueOf(msg);
        String braces = "{}";
        StringBuilder builder = new StringBuilder();
        int start = 0;
        for (Object arg : args)
        {
            int bracesIndex = msg.indexOf(braces, start);
            if (bracesIndex < 0)
            {
                builder.append(msg.substring(start));
                builder.append(" ");
                builder.append(arg);
                start = msg.length();
            }
            else
            {
                builder.append(msg.substring(start, bracesIndex));
                builder.append(String.valueOf(arg));
                start = bracesIndex + braces.length();
            }
        }
        builder.append(msg.substring(start));
        return builder.toString();
    }
}
