package com.zte.mos.logging_service;

import com.zte.mos.massive.monitor.Monitor;
import com.zte.mos.massive.monitor.Sampler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by olinchy on 16-7-21.
 */
public class MonitorFrame extends JFrame
{
    public MonitorFrame() throws HeadlessException
    {
        this.setTitle("message Monitor");
        JScrollPane jscrollPane;
        this.getContentPane().add(jscrollPane = new JScrollPane(), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(
                new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.8), 230));

        for (Sampler sampler : LoggingServiceImpl.SAMPLERS)
        {
            Monitor monitor = new Monitor(sampler);
            monitor.surf.start();
            monitor.setPreferredSize(new Dimension(300, 200));
            panel.add(monitor);
        }

        jscrollPane.getViewport().add(panel);

        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });

        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
}
