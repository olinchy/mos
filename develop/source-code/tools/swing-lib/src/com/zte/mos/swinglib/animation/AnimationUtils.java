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

import java.awt.*;

/**
 * Provides some behavior useful in the animation framework,
 * or to implement custom animation functions and animations.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.7 $
 */
public final class AnimationUtils
{

    private AnimationUtils()
    {
        // Override the default constructor; prevents instantiation.
    }

    /**
     * Invokes the given runnable in the EDT when the specified animation stopped.
     *
     * @param animation the animation that is observed
     * @param runnable  the runnable that will be executed on animation stop
     */
    public static void invokeOnStop(Animation animation, Runnable runnable)
    {
        animation.addAnimationListener(new StopListener(runnable));
    }

    // Helper Code ***********************************************************

    /**
     * Performs a runnable at animation stop in the event dispatch thread (EDT).
     */
    private static final class StopListener extends AnimationAdapter
    {

        private final Runnable runnable;

        private StopListener(Runnable runnable)
        {
            this.runnable = runnable;
        }

        @Override
        public void animationStopped(AnimationEvent e)
        {
            EventQueue.invokeLater(runnable);
        }
    }

}
