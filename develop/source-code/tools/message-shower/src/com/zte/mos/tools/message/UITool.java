package com.zte.mos.tools.message;

import java.awt.*;

/**
 * Created by olinchy on 2/10/15 for mosjava.
 */
public class UITool
{
    static int topMargin = 100;
    static int rightMargin = 15;
    static int gap = 20;

    public static Point getScreenRightTop(Dimension size, Integer pos)
    {
        Toolkit tool = Toolkit.getDefaultToolkit();
        Dimension screenSize = tool.getScreenSize();
        Point p = new Point(screenSize.width - rightMargin - size.width,
                topMargin + size.height * pos + gap * pos);
        return p;
    }

}
