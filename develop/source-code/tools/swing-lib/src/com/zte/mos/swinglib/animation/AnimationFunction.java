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

package com.zte.mos.swinglib.animation;

/**
 * This interface describes time-based animation functions by
 * their duration and a mapping from time to arbitrary values.
 * These values are typically attribute values of an animation target,
 * for example the width of a rectangle, the x position of a point,
 * the color of a line, the alpha value of a panel.<p>
 * <p/>
 * For each time in the function's valid time interval,
 * the {@code #valueAt} method returns a value that can be used
 * to apply an animation effect in an Animation.
 *
 * @param <T> the return type of this function
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 * @see AnimationFunctions
 * @see AbstractAnimationFunction
 */
public interface AnimationFunction<T>
{

    /**
     * Returns the length of this function's valid time interval.
     *
     * @return the length of this function's valid time interval
     */
    long duration();

    /**
     * Returns the function value at a given time in the valid time interval.
     * The value is undefined for times outside this function's time interval.
     * An implementation may throw an exception, if this method called with
     * a time outside the time interval.
     *
     * @param time the time used to determine the animation effect
     * @return the function value at the given time
     */
    T valueAt(long time);

}
