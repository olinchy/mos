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

package com.zte.mos.swinglib.animation.renderer;

import java.awt.*;

import static com.zte.mos.swinglib.common.base.Preconditions.checkArgument;

/**
 * Renders a text with modifiable scaling, color, glyph spacing and position.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.6 $
 */
public final class BasicTextRenderer extends AbstractTextRenderer
{

    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;
    private float space = 0.0f;

    /**
     * Constructs a renderer for a text that can be scaled, moved, and
     * change inter-glyph space.
     *
     * @param text the text to be displayed
     */
    public BasicTextRenderer(String text)
    {
        super(text);
    }

    public float getOffsetX()
    {
        return offsetX;
    }

    public float getOffsetY()
    {
        return offsetY;
    }

    public float getSpace()
    {
        return space;
    }

    public void setOffsetX(float offsetX)
    {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY)
    {
        this.offsetY = offsetY;
    }

    public void setSpace(float space)
    {
        this.space = space;
    }

    public float getScaleX()
    {
        return scaleX;
    }

    public float getScaleY()
    {
        return scaleY;
    }

    public void setScaleX(float scaleX)
    {
        checkArgument(scaleX > 0.0f, "scaleX must be positive.");
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY)
    {
        checkArgument(scaleY > 0.0f, "scaleY must be positive.");
        this.scaleY = scaleY;
    }

    /**
     * Renders the text. Firstly ensures a valid cache, then sets
     * the color, and finally paints the cached glyph shapes.
     *
     * @param g2     the graphics context to render on
     * @param width  the width of the drawing surface
     * @param height the height of the drawing surface
     */
    @Override
    public void render(Graphics2D g2, int width, int height)
    {
        ensureValidCache(g2);

        int glyphCount = cachedGlyphShapes.length;
        float totalSpace = (glyphCount - 1) * space;
        float totalWidth = cachedTextWidth + totalSpace;
        float textHeight = getAdjustedAscent();
        float xOffset = getOffsetX() + (width - totalWidth * scaleX) / 2.0f;
        float yOffset = getOffsetY() + (height + textHeight * scaleY) / 2.0f - getAdjustedDescent();

        g2.setColor(getColor());
        g2.translate(xOffset, yOffset);
        g2.scale(scaleX, scaleY);
        for (int i = 0; i < glyphCount; i++)
        {
            g2.fill(cachedGlyphShapes[i]);
            g2.translate(space, 0);
        }
        g2.scale(1.0f / scaleX, 1.0f / scaleY);
        g2.translate(-totalSpace - xOffset, -yOffset);
    }

}