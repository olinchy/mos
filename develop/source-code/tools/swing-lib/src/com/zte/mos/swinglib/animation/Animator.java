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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.zte.mos.swinglib.common.base.Preconditions.checkArgument;
import static com.zte.mos.swinglib.common.base.Preconditions.checkNotNull;
import static com.zte.mos.swinglib.common.internal.Messages.MUST_NOT_BE_NULL;

/**
 * Starts and stops an animation and triggers
 * the animation at a given frame rate.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 */
public final class Animator implements ActionListener
{

    private final Animation animation;
    private final Timer timer;
    private final int framesPerSecond;

    private long startTime;
    private long elapsedTime = 0;

    // Instance Creation *****************************************************

    /**
     * Constructs an Animator for the given animation and frame rate.
     *
     * @param animation       the animation to animate
     * @param framesPerSecond the desired frame rate
     * @throws NullPointerException     if the animation is {@code null}
     * @throws IllegalArgumentException if the frame rate is non-positive
     */
    public Animator(Animation animation, int framesPerSecond)
    {
        checkArgument(framesPerSecond > 0, "The frame rate must be positive.");
        this.animation = checkNotNull(animation, MUST_NOT_BE_NULL, "animation");
        this.framesPerSecond = framesPerSecond;
        this.timer = createTimer(framesPerSecond);
    }

    // ************************************************************************

    /**
     * Returns the animator's animation.
     *
     * @return the animator's animation
     */
    public Animation animation()
    {
        return animation;
    }

    /**
     * Returns the desired frame rate.
     *
     * @return the desired frame rate per second
     */
    public int framesPerSecond()
    {
        return framesPerSecond;
    }

    /**
     * Returns the elapsed time since animation start.
     *
     * @return time elapsed since the animation start
     */
    public long elapsedTime()
    {
        if (!timer.isRunning())
        {
            return elapsedTime;
        }
        long now = System.currentTimeMillis();
        if (startTime == -1)
        {
            startTime = now;
        }
        return now - startTime + elapsedTime;
    }

    /**
     * Starts the animator and in turn the animation.
     */
    public void start()
    {
        if (!timer.isRunning())
        {
            registerStopListener();
            startTime = -1;
            timer.start();
        }
    }

    /**
     * Stops the animator.
     */
    public void stop()
    {
        if (timer.isRunning())
        {
            elapsedTime = elapsedTime();
            timer.stop();
        }
    }

    /**
     * Implements the ActionListener interface used by the Timer.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        // System.out.println("t=" + elapsedTime());
        animation.animate(elapsedTime());
    }

    /**
     * Returns a string representation for the animator.
     *
     * @return a string representation for the animator
     */
    @Override
    public String toString()
    {
        return "elapsedTime=" + elapsedTime() + "; fps=" + framesPerSecond;
    }

    // Helper Code ************************************************************

    /**
     * Creates and configures a {@code Timer} object.
     *
     * @param fps the frames per second
     * @return a {@code Timer} with the specified frame rate
     */
    private Timer createTimer(int fps)
    {
        int delay = 1000 / fps;

        Timer aTimer = new Timer(delay, this);
        aTimer.setInitialDelay(0);
        aTimer.setCoalesce(true);
        return aTimer;
    }

    /**
     * Registers a listener that stops the animator if the animation stopped.
     */
    private void registerStopListener()
    {
        animation.addAnimationListener(new AnimationAdapter()
        {
            @Override
            public void animationStopped(AnimationEvent e)
            {
                //System.out.println("All animations stopped.");
                stop();
                //animation.animate(animation.duration());
            }
        });
    }

}
