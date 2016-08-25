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

package com.zte.mos.swinglib.animation.swing.components;

import com.zte.mos.swinglib.animation.renderer.BasicTextRenderer;
import com.zte.mos.swinglib.animation.renderer.HeightMode;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing text component that can change the text, x and y scaling,
 * glyph space, x and y offset and alignment mode.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.6 $
 * @see BasicTextRenderer
 */
public final class BasicTextLabel extends JComponent
{

    // Property Names *********************************************************

    public static final String PROPERTY_COLOR = "color";
    public static final String PROPERTY_HEIGHT_MODE = "heightMode";
    public static final String PROPERTY_SCALE = "scale";
    public static final String PROPERTY_SCALE_X = "scaleX";
    public static final String PROPERTY_SCALE_Y = "scaleY";
    public static final String PROPERTY_SPACE = "space";
    public static final String PROPERTY_TEXT = "text";
    public static final String PROPERTY_OFFSET_X = "offsetX";
    public static final String PROPERTY_OFFSET_Y = "offsetY";

    // Fields *****************************************************************

    /**
     * Refers to the object that renders the text onto a graphics2D.
     */
    private final BasicTextRenderer renderer;

    // Instance Creation ******************************************************

    /**
     * Constructs a animation text Swing label with an empty initial text.
     */
    public BasicTextLabel()
    {
        this("");
    }

    /**
     * Constructs a animation text Swing label for the given text.
     *
     * @param text the initial text to be displayed
     */
    public BasicTextLabel(String text)
    {
        renderer = new BasicTextRenderer(text);
    }

    // Public Accessors *******************************************************

    public Color getColor()
    {
        return renderer.getColor();
    }

    public HeightMode getHeightMode()
    {
        return renderer.getHeightMode();
    }

    public float getScale()
    {
        return Math.max(getScaleX(), getScaleX());
    }

    public float getScaleX()
    {
        return renderer.getScaleX();
    }

    public float getScaleY()
    {
        return renderer.getScaleY();
    }

    public float getSpace()
    {
        return renderer.getSpace();
    }

    public float getOffsetX()
    {
        return renderer.getOffsetX();
    }

    public float getOffsetY()
    {
        return renderer.getOffsetY();
    }

    public String getText()
    {
        return renderer.getText();
    }

    public void setColor(Color newColor)
    {
        Color oldColor = getColor();
        if (oldColor.equals(newColor))
        {
            return;
        }
        renderer.setColor(newColor);
        firePropertyChange(PROPERTY_COLOR, oldColor, newColor);
        repaint();
    }

    public void setHeightMode(HeightMode heightMode)
    {
        HeightMode oldMode = getHeightMode();
        renderer.setHeightMode(heightMode);
        firePropertyChange(PROPERTY_HEIGHT_MODE, oldMode, heightMode);
        repaint();
    }

    public void setScale(float newScale)
    {
        float oldScale = getScale();
        renderer.setScaleX(newScale);
        renderer.setScaleY(newScale);
        firePropertyChange(PROPERTY_SCALE, oldScale, newScale);
        repaint();
    }

    public void setScaleX(float newScaleX)
    {
        float oldScaleX = getScaleX();
        if (oldScaleX == newScaleX)
        {
            return;
        }
        float oldScale = getScale();
        renderer.setScaleX(newScaleX);
        firePropertyChange(PROPERTY_SCALE_X, oldScaleX, newScaleX);
        firePropertyChange(PROPERTY_SCALE, oldScale, getScale());
        repaint();
    }

    public void setScaleY(float newScaleY)
    {
        float oldScaleY = getScaleY();
        if (oldScaleY == newScaleY)
        {
            return;
        }
        float oldScale = getScale();
        renderer.setScaleY(newScaleY);
        firePropertyChange(PROPERTY_SCALE_Y, oldScaleY, newScaleY);
        firePropertyChange(PROPERTY_SCALE, oldScale, getScale());
        repaint();
    }

    public void setSpace(float newSpace)
    {
        float oldSpace = getSpace();
        if (oldSpace == newSpace)
        {
            return;
        }
        renderer.setSpace(newSpace);
        firePropertyChange(PROPERTY_SPACE, oldSpace, newSpace);
        repaint();
    }

    public void setOffsetX(float offsetX)
    {
        float oldOffsetX = getOffsetX();
        renderer.setOffsetX(offsetX);
        firePropertyChange(PROPERTY_OFFSET_X, oldOffsetX, offsetX);
        repaint();
    }

    public void setOffsetY(float offsetY)
    {
        float oldOffsetY = getOffsetY();
        renderer.setOffsetY(offsetY);
        firePropertyChange(PROPERTY_OFFSET_Y, oldOffsetY, offsetY);
        repaint();
    }

    public void setText(String newText)
    {
        String oldText = getText();
        if (oldText.equals(newText))
        {
            return;
        }
        renderer.setText(newText);
        firePropertyChange(PROPERTY_TEXT, oldText, newText);
        repaint();
    }

    // Painting ***************************************************************

    /**
     * Paints the component. Enabled anti-aliasing and sets high quality hints,
     * then renderers the component via the underlying renderer.
     *
     * @param g the Graphics object to render on
     */
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        renderer.setFont(getFont());
        renderer.render(g2, getWidth(), getHeight());
    }

}
