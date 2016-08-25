package com.zte.mos.tools.message;

import com.zte.mos.inf.Maybe;
import com.zte.mos.type.Pair;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.tools.message.Available.is;
import static com.zte.mos.tools.message.Available.not;

/**
 * Created by olinchy on 2/9/15 for mosjava.
 */
public class PopUpScheduler
{
    private static final int MAX_SLOTS = 1;
    private static ConcurrentLinkedQueue<Pair<MsgType, String>> queue =
            new ConcurrentLinkedQueue<Pair<MsgType, String>>();
    private static Available[] slots = new Available[MAX_SLOTS];

    private CustomFrame currentFrame = null;

    static
    {
        for (int i = 0; i < slots.length; i++)
        {
            slots[i] = is;
        }
    }

    private final StyledPoper poper;
    private final Thread watcher = new Thread()
    {
        @Override public void run()
        {
            while (true)
            {
                if (queue.size() > 0)
                {
                    Pos pos = availablePos();
                    if (!pos.nothing())
                    {
                        CustomFrame frame = poper.createFrame(queue.poll(), pos.getValue());
                        frame.show();
                        new CloseTimer(frame, getThis(), pos.getValue()).start();
                    }
                }
                try
                {
                    sleep(100);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

    };

    public PopUpScheduler(StyledPoper poper)
    {
        this.poper = poper;
        this.watcher.start();
    }

    public void release(Integer pos)
    {
        assert slots[pos].equals(not);
        slots[pos] = is;
    }

    public synchronized void add(Pair<MsgType, String> msg)
    {
        CustomFrame frame = poper.createFrame(msg, 0);
        if (currentFrame != null)
        {
            currentFrame.dispose();
//            release(0);
        }
        currentFrame = frame;
        currentFrame.show();
        new CloseTimer(currentFrame, getThis(), 0).start();

//        queue.offer(msg);
    }

    private PopUpScheduler getThis()
    {
        return this;
    }

    private Pos availablePos()
    {
        for (int i = 0; i < slots.length; i++)
        {
            Available slot = slots[i];
            if (slot.equals(is))
            {
                slots[i] = not;
                return new Pos(i);
            }
        }
        return new Pos();
    }

    private class Pos extends Maybe<Integer>
    {
        public Pos(int i)
        {
            super(i);
        }

        public Pos()
        {
            super();
        }
    }
}
