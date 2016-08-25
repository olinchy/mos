/*
 * Copyright (c) 2001-2013 JGoodies Software GmbH. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Software GmbH nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.zte.mos.swinglib.looks.plastic;

import com.zte.mos.swinglib.looks.common.ExtBasicMenuUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

/**
 * The JGoodies Plastic look&amp;feel implementation of {@code MenuUI}.
 * It differs from the superclass in that it uses an overhauled menu
 * rendering an aligmnent system. Furthermore, you can set a client property
 * <tt>Options.NO_ICONS_KEY</tt> to indicate that this menu has no icons.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.14 $
 * @see    com.zte.mos.swinglib.looks.Options
 */
public final class PlasticMenuUI extends ExtBasicMenuUI
{

    private boolean oldOpaque;

    public static ComponentUI createUI(JComponent b)
    {
        return new PlasticMenuUI();
    }

    @Override
    protected void installDefaults()
    {
        super.installDefaults();
        oldOpaque = menuItem.isOpaque();
    }

    /**
     * Makes the item transparent, if it is not a sub menu and the model is not selected.
     */
    @Override
    protected void paintMenuItem(Graphics g, JComponent c, Icon aCheckIcon,
            Icon anArrowIcon, Color background, Color foreground, int textIconGap)
    {
        JMenuItem b = (JMenuItem) c;

        if (((JMenu) menuItem).isTopLevelMenu())
        {
            b.setOpaque(false);
            if (b.getModel().isSelected())
            {
                int menuWidth = menuItem.getWidth();
                int menuHeight = menuItem.getHeight();
                Color oldColor = g.getColor();
                g.setColor(background);
                g.fillRect(0, 0, menuWidth, menuHeight);
                g.setColor(oldColor);
            }
        }
        super.paintMenuItem(g, c, aCheckIcon, anArrowIcon, background, foreground, textIconGap);
    }

}