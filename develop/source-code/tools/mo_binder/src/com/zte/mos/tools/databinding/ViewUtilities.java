package com.zte.mos.tools.databinding;

import java.awt.*;

/**
 * Created by olinchy on 15-8-11.
 */
public class ViewUtilities
{
    public static void setLocation(Window view, Location location)
    {
        Dimension size = view.getSize();
        Point pos = getPos(size, location);
        view.setLocation(pos);
    }

    private static Point getPos(Dimension size, Location location)
    {
        double para;
        switch (location)
        {
            case Golden:
                para = 1 - 0.618;
                break;
            case Center:
                para = 0.5;
                break;
            default:
                para = 0.5;
        }
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
        int y = (int) ((Toolkit.getDefaultToolkit().getScreenSize().height - size.height) * para);
        return new Point(x, y);
    }

    public enum Location
    {
        Center, Golden
    }
}
