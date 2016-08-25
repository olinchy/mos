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

import com.zte.mos.swinglib.animation.AnimationFunction;
import com.zte.mos.swinglib.animation.AnimationFunctions;
import com.zte.mos.swinglib.animation.renderer.GlyphRenderer;
import com.zte.mos.swinglib.animation.renderer.HeightMode;

import javax.swing.*;
import java.awt.*;

/**
 * A Swing component that can transform a text's individual glyphs.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 */
public final class GlyphLabel extends JComponent
{

    // Property Names *********************************************************

    public static final String PROPERTY_HEIGHT_MODE = "heightMode";
    public static final String PROPERTY_TEXT = "text";
    public static final String PROPERTY_TIME = "time";

    // Fields *****************************************************************

    /**
     * Refers to the renderer that paints the individual glyphs.
     */
    private final GlyphRenderer renderer;

    // Instance Creation ******************************************************

    /**
     * Creates a {@code GlyphLabel} for the given text, duration and
     * delay between the individual glyphs.
     *
     * @param text       the initial text
     * @param duration   the duration of the whole animation
     * @param glyphDelay a delay between the animation of the individual glyphs
     */
    public GlyphLabel(String text, long duration, long glyphDelay)
    {
        this(text, duration, glyphDelay, Color.DARK_GRAY);
    }

    /**
     * Creates a {@code GlyphLabel} for the given text, duration, base color
     * and delay between the individual glyphs.
     *
     * @param text       the initial text
     * @param duration   the duration of the whole animation
     * @param glyphDelay a delay between the animation of the individual glyphs
     * @param baseColor  the color used as a basis for the translucent
     *                   glyph foreground colors
     */
    public GlyphLabel(
            String text,
            long duration,
            long glyphDelay,
            Color baseColor)
    {
        renderer =
                new GlyphRenderer(
                        text,
                        defaultScaleFunction(duration),
                        AnimationFunctions.ZERO,
                        defaultColorFunction(duration, baseColor),
                        glyphDelay);
    }

    /**
     * Creates and returns the default scale function for the given duration.
     *
     * @param duration the duration of the whole animation
     * @return an animation function that maps times to glyph scales
     */
    public static AnimationFunction<Float> defaultScaleFunction(long duration)
    {
        return AnimationFunctions.linear(
                duration,
                new Float[] {
                        new Float(5.0f),
                        new Float(0.8f),
                        new Float(1.0f),
                        new Float(1.0f) },
                new float[] { 0.0f, 0.1f, 0.12f, 1.0f });
    }

    /**
     * Creates and returns the default color function for the given duration
     * and base color.
     *
     * @param duration  the duration of the animation
     * @param baseColor the color used as a basis for the translucent colors
     * @return an animation function that maps times to translucent glyph colors
     */
    public static AnimationFunction<Color> defaultColorFunction(
            long duration,
            Color baseColor)
    {
        return AnimationFunctions.alphaColor(
                AnimationFunctions.linear(
                        duration,
                        new Integer[] {
                                new Integer(0),
                                new Integer(255),
                                new Integer(255) },
                        new float[] { 0.0f, 0.15f, 1.0f }),
                baseColor);
    }

    // Accessors **************************************************************

    public HeightMode getHeightMode()
    {
        return renderer.getHeightMode();
    }

    public String getText()
    {
        return renderer.getText();
    }

    public long getTime()
    {
        return renderer.getTime();
    }

    public void setHeightMode(HeightMode newHeightMode)
    {
        HeightMode oldHeightMode = getHeightMode();
        renderer.setHeightMode(newHeightMode);
        firePropertyChange(PROPERTY_HEIGHT_MODE, oldHeightMode, newHeightMode);
        repaint();
    }

    public void setText(String newText)
    {
        String oldText = getText();
        renderer.setText(newText);
        firePropertyChange(PROPERTY_TEXT, oldText, newText);
        repaint();
    }

    public void setTime(long newTime)
    {
        long oldTime = getTime();
        renderer.setTime(newTime);
        firePropertyChange(PROPERTY_TIME, oldTime, newTime);
        repaint();
    }

    // Rendering **************************************************************

    /**
     * Paints the component. Sets high-fidelity rendering hints,
     * then invoke the renderer to render the glyphs.
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
