package com.zte.mos.persistent;

@SuppressWarnings("unused")
public class PersistentHolder
{
    private PersistentContext persistent;

    private PersistentHolder()
    {
    }

    public PersistentContext getPersistent()
    {
        return persistent;
    }

    public void setPersistent(PersistentContext persistent)
    {
        this.persistent = persistent;
    }
}
