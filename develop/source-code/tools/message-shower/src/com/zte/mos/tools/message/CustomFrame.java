package com.zte.mos.tools.message;

import com.sun.awt.AWTUtilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public final class CustomFrame<T extends BasicFrame>
{
    private final T frame;
    private final Shower shower;
    private final Disposer disposer;
    private Timer show = null;
    private Timer dispose = null;

    public CustomFrame(T t)
    {
        this.frame = t;
        frame.setUndecorated(true);
        AWTUtilities.setWindowShape(frame,
                new RoundRectangle2D.Double(0.0D, 0.0D, frame.getWidth(),
                        frame.getHeight(), 6.0D, 6.0D));
        this.shower = new Shower(frame);
        this.disposer = new Disposer(frame);
        show = new Timer(20, shower);
        dispose = new Timer(20, disposer);

    }

    public void show()
    {
        dispose.stop();
        show.start();
    }

    public void dispose()
    {
        show.stop();
        dispose.start();
    }

    abstract class UIController implements ActionListener
    {
        protected static final float opacityUp = 1.0f;
        protected static final float opacityInterval = 0.02f;
        protected static final float opacityDown = opacityInterval;
        protected final BasicFrame frame;
        protected float current = 0.0f;
        protected boolean startOver = true;

        UIController(BasicFrame frame)
        {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (startOver)
            {
                frameActionPre();
                startOver = false;
            }
            if (isLast())
            {
                frameActionPost();
            }
            else
            {
                refreshOpacity(countOpacity());
            }
        }

        protected void frameActionPre()
        {
        }

        protected abstract boolean isLast();

        protected void frameActionPost()
        {
        }

        private void refreshOpacity(final float opacity)
        {
            if (opacity <= opacityUp && opacity >= opacityDown)
            {
                this.current = opacity;
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            AWTUtilities.setWindowOpacity(frame, opacity);
                        }catch (UnsupportedOperationException e){

                        }
                    }
                });
            }
        }

        protected abstract float countOpacity();
    }

    class Shower extends UIController
    {
        Shower(BasicFrame frame)
        {
            super(frame);
        }

        @Override
        protected boolean isLast()
        {
            return current >= frame.getTarOpacity();
        }

        @Override
        protected void frameActionPost()
        {
            show.stop();
            this.startOver = true;
            frame.startWorking();
        }

        @Override
        protected float countOpacity()
        {
            if (current < frame.getTarOpacity())
            {
                current += opacityInterval;
                return current;
            }
            return current;
        }

        @Override
        protected void frameActionPre()
        {
            current = opacityDown;
            try {
                AWTUtilities.setWindowOpacity(frame, current);
            }catch (UnsupportedOperationException e){

            }

            SwingUtilities.invokeLater(new Runnable()
            {
                @Override public void run()
                {
                    frame.setVisible(true);
                }
            });
        }

    }

    class Disposer extends UIController
    {

        Disposer(BasicFrame frame)
        {
            super(frame);
        }

        @Override
        protected float countOpacity()
        {
            if (current > opacityDown)
            {
                current -= opacityInterval;
                return current;
            }
            return current;
        }

        @Override
        protected void frameActionPost()
        {
            dispose.stop();
            this.startOver = true;
            SwingUtilities.invokeLater(new Runnable()
            {
                @Override public void run()
                {
                    frame.setVisible(false);
                }
            });
        }

        @Override
        protected boolean isLast()
        {
            return current <= opacityDown;
        }

        @Override
        protected void frameActionPre()
        {
            current = frame.getTarOpacity();
        }

    }

}
