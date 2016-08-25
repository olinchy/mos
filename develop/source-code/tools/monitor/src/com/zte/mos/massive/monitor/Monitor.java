/*
 *
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.zte.mos.massive.monitor;

import com.zte.mos.logging_service.Log;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import static java.awt.Color.*;

/**
 * Tracks what Sampler gives, displayed in graph form.
 */
@SuppressWarnings("serial")
public class Monitor extends JPanel
{
    public Surface surf;
    private Sampler sampler;

    public Monitor(Sampler sampler)
    {
        this.sampler = sampler;
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(new EtchedBorder(), this.sampler.name() + " Monitor"));
        add(surf = new Surface());
        Font font = new Font("serif", Font.PLAIN, 10);
        JLabel label = new JLabel("Sample Rate");
        label.setFont(font);
        label.setForeground(BLACK);
        label.setFont(font);
        label.setForeground(BLACK);
    }

    public class Surface extends JPanel implements Runnable
    {
        Thread thread;
        long sleepAmount = 1000;
        private int w, h;
        private BufferedImage bimg;
        private Graphics2D big;
        private Font font = new Font("Times New Roman", Font.PLAIN, 11);
        private int columnInc;
        private int pts[];
        private int ptNum;
        private int ascent, descent;
        private Rectangle graphOutlineRect = new Rectangle();
        private Line2D graphLine = new Line2D.Float();
        private Color graphColor = new Color(46, 139, 87);

        public Surface()
        {
            setBackground(BLACK);
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (thread == null)
                    {
                        start();
                    }
                    else
                    {
                        stop();
                    }
                }
            });
        }

        @Override
        public Dimension getMinimumSize()
        {
            return getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize()
        {
            return getPreferredSize();
        }

        @Override
        public Dimension getPreferredSize()
        {
            return new Dimension(135, 80);
        }

        @Override
        public void paint(Graphics g)
        {

            if (big == null)
            {
                return;
            }

            big.setBackground(getBackground());
            big.clearRect(0, 0, w, h);

            // .. Draw allocated and used strings ..
            big.setColor(GREEN);
            big.drawString(sampler.maxY(), 4.0f, ascent + 0.5f);
            big.drawString(sampler.startY(), 4, h - descent);

            // Calculate remaining size
            float ssH = ascent + descent;
            float remainingHeight = (h - (ssH * 2) - 0.5f);

            // .. Draw History Graph ..
            big.setColor(graphColor);
            int graphX = 5;
            int graphY = (int) ssH;
            int graphW = w - graphX - 5;
            int graphH = (int) remainingHeight;
            graphOutlineRect.setRect(graphX, graphY, graphW, graphH);
            big.draw(graphOutlineRect);

            int graphRow = graphH / 10;

            // .. Draw row ..
            for (int j = graphY; j <= graphH + graphY; j += graphRow)
            {
                graphLine.setLine(graphX, j, graphX + graphW, j);
                big.draw(graphLine);
            }

            // .. Draw animated column movement ..
            int graphColumn = graphW / 15;

            if (columnInc == 0)
            {
                columnInc = graphColumn;
            }

            for (int j = graphX + columnInc; j < graphW + graphX; j +=
                    graphColumn)
            {
                graphLine.setLine(j, graphY, j, graphY + graphH);
                big.draw(graphLine);
            }

            --columnInc;

            if (pts == null)
            {
                pts = new int[graphW];
                ptNum = 0;
            }
            else if (pts.length != graphW)
            {
                int tmp[] = null;
                if (ptNum < graphW)
                {
                    tmp = new int[ptNum];
                    System.arraycopy(pts, 0, tmp, 0, tmp.length);
                }
                else
                {
                    tmp = new int[graphW];
                    System.arraycopy(pts, pts.length - tmp.length, tmp, 0,
                                     tmp.length);
                    ptNum = tmp.length - 2;
                }
                pts = new int[graphW];
                System.arraycopy(tmp, 0, pts, 0, tmp.length);
            }
            else
            {
                big.setColor(YELLOW);
                pts[ptNum] =
                        (int) (graphY + graphH * (1 - sampler.rate()));
                for (int j = graphX + graphW - ptNum, k = 0; k < ptNum; k++, j++)
                {
                    if (k != 0)
                    {
                        if (pts[k] != pts[k - 1])
                        {
                            big.drawLine(j - 1, pts[k - 1], j, pts[k]);
                        }
                        else
                        {
                            big.fillRect(j, pts[k], 1, 1);
                        }
                    }
                }
                if (ptNum + 2 == pts.length)
                {
                    // throw out oldest point
                    for (int j = 1; j < ptNum; j++)
                    {
                        pts[j - 1] = pts[j];
                    }
                    --ptNum;
                }
                else
                {
                    ptNum++;
                }
            }
            g.drawImage(bimg, 0, 0, this);
        }

        public void start()
        {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setName("Monitor");
            thread.start();
        }

        public synchronized void stop()
        {
            thread = null;
            notify();
        }

        @Override
        @SuppressWarnings("SleepWhileHoldingLock")
        public void run()
        {

            Thread me = Thread.currentThread();

            while (thread == me && !isShowing() || getSize().width == 0)
            {
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }

            while (thread == me && isShowing())
            {
                Dimension d = getSize();
                if (d.width != w || d.height != h)
                {
                    w = d.width;
                    h = d.height;
                    bimg = (BufferedImage) createImage(w, h);
                    big = bimg.createGraphics();
                    big.setFont(font);
                    FontMetrics fm = big.getFontMetrics(font);
                    ascent = fm.getAscent();
                    descent = fm.getDescent();
                }
                repaint();
                try
                {
                    Thread.sleep(sleepAmount);
                }
                catch (InterruptedException e)
                {
                    break;
                }
            }
            thread = null;
        }
    }

    public static void main(String s[])
    {
        final Monitor demo = new Monitor(new Sampler()
        {
            @Override
            public String name()
            {
                return "Memory";
            }

            @Override
            public String maxY()
            {
                return String.valueOf(Runtime.getRuntime().totalMemory() / 1024 + " KB in total");
            }

            @Override
            public String startY()
            {
                return String.valueOf(
                        (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 + " KB allocated");
            }

            @Override
            public float rate()
            {
                float free = Runtime.getRuntime().freeMemory();
                float total = Runtime.getRuntime().totalMemory();
                return 1 - free / total;
            }

            @Override
            public void took(Log log)
            {

            }

            @Override
            public boolean support(Log log)
            {
                return true;
            }
        });
        WindowListener l = new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }

            @Override
            public void windowDeiconified(WindowEvent e)
            {
                demo.surf.start();
            }

            @Override
            public void windowIconified(WindowEvent e)
            {
                demo.surf.stop();
            }
        };
        JFrame f = new JFrame("Monitor");
        f.addWindowListener(l);
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(300, 300));
        f.setVisible(true);
        demo.surf.start();
    }
}
