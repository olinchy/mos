package com.zte.mos.task;

/**
 * Created by ccy on 4/25/16.
 */
public abstract class SchedulingTask implements Runnable
{
    protected String type()
    {
        return this.getClass().getSimpleName();
    }

    protected abstract String identity();

    public String toString()
    {
        return "taskType: " + type() + " taskIdentity: " + identity();
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof  SchedulingTask))
        {
            return false;
        }
        SchedulingTask other = (SchedulingTask)o;

        if (!other.identity().equalsIgnoreCase(this.identity()))
        {
            return false;
        }

        if (!other.type().equalsIgnoreCase(this.type()))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        return toString().hashCode();
    }

}
