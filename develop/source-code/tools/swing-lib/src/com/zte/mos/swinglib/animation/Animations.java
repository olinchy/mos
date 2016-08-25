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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.zte.mos.swinglib.common.base.Preconditions.checkArgument;

/**
 * This class consists only of static methods that either
 * operate on animations or create useful standard animations.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 */
public final class Animations
{

    private Animations()
    {
        // Overrides the default constructor; prevents instantiation.
    }

    /**
     * Creates and returns an animation that is defined by a given
     * animation and offset; the resulting animation applies
     * the original effect shifted in time.
     *
     * @param animation the animation to shift
     * @param beginTime the time to begin the shifted animation
     * @return the shifted animation
     */
    public static Animation offset(Animation animation, long beginTime)
    {
        return new OffsetAnimation(beginTime, animation);
    }

    /**
     * Creates and returns a parallel time container, that is an animation
     * that applies the effect of the given animations all at the same time.
     *
     * @param animations a {@code List} of animations
     * @return a parallel time container for the given animations
     */
    public static Animation parallel(List<Animation> animations)
    {
        return new ParallelAnimation(animations);
    }

    /**
     * Creates and returns a parallel time container for the given animations,
     * that is an animation that applies the effect of the given animations
     * at the same time.
     *
     * @param animations the animations to be parallized
     * @return the parallelized animation
     */
    public static Animation parallel(Animation... animations)
    {
        return parallel(Arrays.asList(animations));
    }

    /**
     * Creates and returns a pausing animation that has no effect
     * but a duration. It is useful in combination with sequenced
     * and parallel time containers.
     *
     * @param duration the pause duration
     * @return an animation that has no effect
     */
    public static Animation pause(long duration)
    {
        return new PauseAnimation(duration);
    }

    /**
     * Creates and returns an animation that is defined by repeating the given
     * animation. The result's duration is the duration times repeatCount.
     *
     * @param animation   the animation to repeat
     * @param repeatCount the number of repetitions
     * @return the repeated animation
     */
    public static Animation repeat(Animation animation, float repeatCount)
    {
        return repeat(animation, (long) (animation.duration() * repeatCount));
    }

    /**
     * Creates and returns an animation that is defined by repeating
     * the given animation for the given duration.
     *
     * @param animation the animation to repeat
     * @param duration  the duration of the repeated animation
     * @return the repeated animation
     */
    public static Animation repeat(Animation animation, long duration)
    {
        return new RepeatedAnimation(duration, animation);
    }

    /**
     * Creates and returns an animation that is defined by reverting
     * the given animation over the time.
     *
     * @param animation the animation to reverse
     * @return the reversed animation
     */
    public static Animation reverse(Animation animation)
    {
        return new ReversedAnimation(animation);
    }

    /**
     * Creates and returns a sequenced time container that is an animation,
     * that concatenates the given list of animations over the time.
     *
     * @param animations a {@code List} of animations
     * @return the sequenced animation
     */
    public static Animation sequential(List<Animation> animations)
    {
        return new SequencedAnimation(animations);
    }

    /**
     * Creates and returns a sequenced time container that is an animation,
     * that concatenates the given array of animations over the time.
     *
     * @param animations the animations to be sequenced
     * @return the sequenced animation
     */
    public static Animation sequential(Animation... animations)
    {
        return sequential(Arrays.asList(animations));
    }

    // Helper Classes *********************************************************

    /**
     * Helper class that wraps an animation to give it a time offset.
     */
    private static final class OffsetAnimation extends AbstractAnimation
    {

        private final Animation animation;
        private final long beginTime;

        private OffsetAnimation(long beginTime, Animation animation)
        {
            super(beginTime + animation.duration(), true);
            this.animation = animation;
            this.beginTime = beginTime;
        }

        @Override
        protected void applyEffect(long time)
        {
            long relativeTime = time - beginTime;
            if (relativeTime >= 0)
            {
                animation.animate(relativeTime);
            }
        }

    }

    /**
     * Used to apply an effect one-time only.
     */
    public abstract static class OneTimeAnimation extends AbstractAnimation
    {

        private boolean effectApplied;

        /**
         * Constructs a {@code OneTimeAnimation}.
         */
        public OneTimeAnimation()
        {
            super(0, true);
            effectApplied = false;
        }

        /**
         * Applies the effect to the animation target,
         * only if is hasn't been applied before.
         *
         * @param time the time used to determine the animation effect
         */
        @Override
        public void animate(long time)
        {
            if (effectApplied)
            {
                return;
            }

            fireAnimationStarted(time);
            applyEffect(time);
            fireAnimationStopped(time);
            effectApplied = true;
        }
    }

    /**
     * Helper class to parallelize animations.
     */
    private static final class ParallelAnimation extends AbstractAnimation
    {

        private final List<Animation> animations;

        private ParallelAnimation(List<Animation> animations)
        {
            super(maxDuration(animations), true);
            this.animations = Collections.unmodifiableList(animations);
        }

        private static long maxDuration(List<Animation> animations)
        {
            long maxDuration = 0;
            for (Animation animation : animations)
            {
                long duration = animation.duration();
                if (duration > maxDuration)
                {
                    maxDuration = duration;
                }
            }
            return maxDuration;
        }

        @Override
        protected void applyEffect(long time)
        {
            for (Animation animation : animations)
            {
                animation.animate(time);
            }
        }

    }

    /**
     * Helper class for a pause, an animation, that has no effect.
     */
    private static final class PauseAnimation extends AbstractAnimation
    {
        PauseAnimation(long duration)
        {
            super(duration, true);
        }

        @Override
        protected void applyEffect(long time)
        {
            // Just pause, do nothing.
        }
    }

    /**
     * Helper class to repeat an animation.
     */
    private static final class RepeatedAnimation extends AbstractAnimation
    {
        private final Animation animation;
        private final long simpleDuration;

        private RepeatedAnimation(long duration, Animation animation)
        {
            super(duration, true);
            this.animation = animation;
            this.simpleDuration = animation.duration();
        }

        @Override
        protected void applyEffect(long time)
        {
            animation.animate(time % simpleDuration);
        }
    }

    /**
     * Helper class to reverse an animation over the time.
     */
    private static final class ReversedAnimation extends AbstractAnimation
    {
        private final Animation animation;

        private ReversedAnimation(Animation animation)
        {
            super(animation.duration(), true);
            this.animation = animation;
        }

        @Override
        protected void applyEffect(long time)
        {
            long reversedTime = duration() - time;
            checkArgument(reversedTime >= 0, "The time must be in the valid time interval.");

            animation.animate(reversedTime);
        }
    }

    /**
     * Helper class to create a sequence of animations.
     */
    private static final class SequencedAnimation extends AbstractAnimation
    {

        private final List<Animation> animations;

        private SequencedAnimation(List<Animation> animations)
        {
            super(cumulatedDuration(animations), true);
            checkArgument(!animations.isEmpty(), "The list of animations must not be empty.");
            this.animations = Collections.unmodifiableList(animations);
        }

        private static long cumulatedDuration(List<Animation> animations)
        {
            long cumulatedDuration = 0;
            for (Animation animation : animations)
            {
                cumulatedDuration += animation.duration();
            }
            return cumulatedDuration;
        }

        @Override
        protected void applyEffect(long time)
        {
            long startTime = 0;
            for (Animation animation : animations)
            {
                long relativeTime = time - startTime;
                if (relativeTime > 0)
                {
                    animation.animate(relativeTime);
                }
                startTime += animation.duration();
            }
        }

    }

}
