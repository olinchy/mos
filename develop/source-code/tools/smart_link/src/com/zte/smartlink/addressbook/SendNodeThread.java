package com.zte.smartlink.addressbook;

public abstract class SendNodeThread extends Thread
{
    protected final NodeAddress[] nodes;

    public SendNodeThread(NodeAddress[] nodes)
    {
        this.nodes = nodes;
    }

    public SendNodeThread(NodeAddress node)
    {
        this(new NodeAddress[] { node });
    }

    @Override
    public void run()
    {
        try
        {
            send();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public abstract void send() throws Exception;
}
