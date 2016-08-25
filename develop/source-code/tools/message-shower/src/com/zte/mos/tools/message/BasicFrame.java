package com.zte.mos.tools.message;

import javax.swing.*;
import java.awt.*;

/**
 * Created by olinchy on 2/9/15 for mosjava.
 */
public abstract class BasicFrame extends JDialog
{

    public BasicFrame() throws HeadlessException
    {
        this.setModal(false);
        init();
    }

    protected abstract void init();

    public float getTarOpacity()
    {
        return 1f;
    }

    public abstract void startWorking();
}
