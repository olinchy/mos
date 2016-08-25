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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class consists only of static methods that construct and operate on
 * {@link AnimationFunction}s.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.11 $
 * @see AnimationFunction
 */
public final class AnimationFunctions
{

    /**
     * A constant {@link AnimationFunction} that returns
     * {@code 1f} all the time.
     */
    public static final AnimationFunction<Float> ONE =
            constant(Integer.MAX_VALUE, new Float(1.0f));

    /**
     * A constant {@link AnimationFunction} that returns
     * {@code 0f} all the time.
     */
    public static final AnimationFunction<Float> ZERO =
            constant(Integer.MAX_VALUE, new Float(0.0f));

    private AnimationFunctions()
    {
        // Overrides the default constructor; prevents instantiation.
    }

    /**
     * Creates and returns an animation function that returns time-based
     * sRGB colors that are built from a given base color and
     * an animation function of alpha values.
     * <p/>
     * Useful for fading effects.
     *
     * @param f         the animation function of alpha values
     * @param baseColor the base color
     * @return an animation function of colors
     */
    public static AnimationFunction<Color> alphaColor(
            AnimationFunction<Integer> f,
            Color baseColor)
    {
        return new AlphaColorAnimationFunction(f, baseColor);
    }

    /**
     * Concatenates the given animation functions and returns a compound
     * animation function that represents the concatenation.
     *
     * @param functions the animation functions to concatenate
     * @return the concatenated animation function
     */
    public static <T> AnimationFunction<T> concat(AnimationFunction<T>... functions)
    {
        return new SequencedAnimationFunction<T>(Arrays.asList(functions));
    }

    /**
     * Creates and returns an animation function that returns a constant value
     * over the given duration.
     *
     * @param duration the function's duration
     * @param value    the Object that will be returned all the time
     * @return a constant animation function
     */
    public static <T> AnimationFunction<T> constant(long duration, T value)
    {
        return discrete(duration, value);
    }

    /**
     * Creates and returns a discrete animation function for the
     * given duration and values. The values are equally distributed
     * over the duration.
     *
     * @param duration the function's duration
     * @param values   an array of discrete result values
     * @return a discrete animation function
     */
    public static <T> AnimationFunction<T> discrete(
            long duration,
            T... values)
    {
        return discrete(duration, values, null);
    }

    /**
     * Creates and returns a discrete animation function for the given duration,
     * values and interpolation key times.
     *
     * @param duration the function's duration
     * @param values   an array of discrete result values
     * @param keyTimes an array of key times used to distribute the
     *                 result values over the time
     * @return a discrete animation function
     */
    public static <T> AnimationFunction<T> discrete(
            long duration,
            T[] values,
            float[] keyTimes)
    {
        return new InterpolatedAnimationFunction<T>(
                duration,
                values,
                keyTimes,
                InterpolationMode.DISCRETE);
    }

    /**
     * Creates and returns a linear animation function for the given duration
     * that returns Float in interval [from, from + by].
     *
     * @param duration the animation duration
     * @param from     the initial result value
     * @param by       the difference that is added to the initial value
     * @return a linear animation function with values in [from, from + by]
     */
    public static AnimationFunction<Float> fromBy(
            long duration,
            float from,
            float by)
    {
        return fromTo(duration, from, from + by);
    }

    /**
     * Creates and returns a linear animation function with the given duration.
     * The function values are Floats in the interval [from, to].
     *
     * @param duration the animation duration
     * @param from     the initial result value
     * @param to       the last result value
     * @return a linear animation function with values in [from, to]
     */
    public static AnimationFunction<Float> fromTo(
            long duration,
            float from,
            float to)
    {
        return linear(
                duration,
                new Float[] { Float.valueOf(from), Float.valueOf(to) });
    }

    /**
     * Creates and returns a linear animation function that is defined
     * by an array of numeric values; these are distributed equally
     * over the duration.
     *
     * @param duration the animation duration
     * @param values   an array of values
     * @return a linear animation function for the given values
     */
    public static <T extends Number> AnimationFunction<T> linear(long duration, T[] values)
    {
        return linear(duration, values, null);
    }

    /**
     * Creates and returns a linear animation function that is defined
     * by an array of numeric values and an array of relative key times.
     *
     * @param duration the animation duration
     * @param values   an array of values
     * @param keyTimes an array of key times used to distribute the
     *                 result values over the time
     * @return a linear animation function for the given values
     */
    public static <T extends Number> AnimationFunction<T> linear(
            long duration,
            T[] values,
            float[] keyTimes)
    {
        return new InterpolatedAnimationFunction(
                duration,
                values,
                keyTimes,
                InterpolationMode.LINEAR);
    }

    /**
     * Creates an {@code AnimationFunction} that maps times
     * to instances of {@code Color}. The mapping is interpolated
     * from an array of Colors using an array of key times.
     *
     * @param duration the duration of this animation function
     * @param colors   the colors to interpolate.
     * @param keyTimes an array of key times used to distribute
     *                 the result values over the time.
     * @return An {@link AnimationFunction} that maps times to sRGB colors.
     * This mapping is defined by an array of {@code Color} values
     * and a corresponding array of key times that is used to interpolate
     * sub-AnimationFunction for the red, green, blue and alpha values.
     */
    public static AnimationFunction<Color> linearColors(
            long duration, Color[] colors, float[] keyTimes)
    {
        return new ColorFunction(duration, colors, keyTimes);
    }

    /**
     * Creates and returns an animation function that returns random values
     * from the interval [min, max] with a given change probability.
     *
     * @param min               the minimum result value
     * @param max               the maximum result value
     * @param changeProbability the probability that the value changes
     * @return an animation function with random values in [min, max]
     */
    public static AnimationFunction<Integer> random(
            int min,
            int max,
            float changeProbability)
    {
        return new RandomAnimationFunction(min, max, changeProbability);
    }

    /**
     * Creates and returns an animation function that is defined
     * by repeating the specified animation function.
     *
     * @param f          the animation function to repeat
     * @param repeatTime the time to repeat the function
     * @return the repeated animation function
     */
    public static <T> AnimationFunction<T> repeat(
            AnimationFunction<T> f,
            long repeatTime)
    {
        return new RepeatedAnimationFunction<T>(f, repeatTime);
    }

    /**
     * Creates and returns an animation function that is defined
     * by reverting the given animation function in time.
     *
     * @param f the animation function to reverse
     * @return the reversed animation function
     */
    public static <T> AnimationFunction<T> reverse(AnimationFunction<T> f)
    {
        return new ReversedAnimationFunction<T>(f);
    }

    // Helper Classes *********************************************************

    /**
     * Helper class for animation functions that answer translucent colors.
     */
    private static final class AlphaColorAnimationFunction
            implements AnimationFunction<Color>
    {

        private final Color baseColor;
        private final AnimationFunction<Integer> fAlpha;

        private AlphaColorAnimationFunction(
                AnimationFunction<Integer> fAlpha,
                Color baseColor)
        {
            this.fAlpha = fAlpha;
            this.baseColor = baseColor;
        }

        @Override
        public long duration()
        {
            return fAlpha.duration();
        }

        // Constructs colors from the sRGB color space.
        @Override
        public Color valueAt(long time)
        {
            return new Color(
                    baseColor.getRed(),
                    baseColor.getGreen(),
                    baseColor.getBlue(),
                    fAlpha.valueAt(time));
        }
    }

    /**
     * Helper class for interpolating colors.
     */
    private static final class ColorFunction extends AbstractAnimationFunction<Color>
    {

        /**
         * Refers to an AnimationFunction of float values that maps
         * a time to the red component of an sRGB color value.
         */
        private final AnimationFunction<Integer> redFunction;

        /**
         * Refers to an AnimationFunction of float values that maps
         * a time to the green component of an sRGB color value.
         */
        private final AnimationFunction<Integer> greenFunction;

        /**
         * Refers to an AnimationFunction of float values that maps
         * a time to the blue component of an sRGB color value.
         */
        private final AnimationFunction<Integer> blueFunction;

        /**
         * Refers to an AnimationFunction of float values that maps
         * a time to the alpha value of an sRGB color value.
         */
        private final AnimationFunction<Integer> alphaFunction;

        // Instance creation ******************************************************

        /**
         * Creates an {@code AnimationFunction} that maps times
         * to instances of {@code Color}. The mapping is interpolated
         * from an array of Colors using an array of key times.
         *
         * @param duration the duration of this animation function
         * @param colors   the colors to interpolate.
         * @param keyTimes an array of key times used to distribute
         *                 the result values over the time.
         */
        private ColorFunction(long duration, Color[] colors, float[] keyTimes)
        {
            super(duration);
            Integer[] red = new Integer[colors.length];
            Integer[] green = new Integer[colors.length];
            Integer[] blue = new Integer[colors.length];
            Integer[] alpha = new Integer[colors.length];

            for (int i = 0; i < colors.length; i++)
            {
                red[i] = Integer.valueOf(colors[i].getRed());
                green[i] = Integer.valueOf(colors[i].getGreen());
                blue[i] = Integer.valueOf(colors[i].getBlue());
                alpha[i] = Integer.valueOf(colors[i].getAlpha());
            }

            redFunction = AnimationFunctions.linear(duration, red, keyTimes);
            greenFunction = AnimationFunctions.linear(duration, green, keyTimes);
            blueFunction = AnimationFunctions.linear(duration, blue, keyTimes);
            alphaFunction = AnimationFunctions.linear(duration, alpha, keyTimes);
        }

        // AnimationFunction Implementation ***************************************

        /**
         * Returns the interpolated color for a given time
         * in the valid time interval.
         *
         * @param time the time used to determine the interpolated color
         * @return the interpolated color for the given time
         */
        @Override
        public Color valueAt(long time)
        {
            checkTimeRange(time);
            return new Color(
                    redFunction.valueAt(time),
                    greenFunction.valueAt(time),
                    blueFunction.valueAt(time),
                    alphaFunction.valueAt(time));
        }

    }

    public enum InterpolationMode
    {
        DISCRETE,
        LINEAR
        /* Left for long winter nights in northern Germany.
        PACED,
        SPLINE
         */
    }

    /**
     * Helper class for interpolation based animation functions.
     */
    private static final class InterpolatedAnimationFunction<T>
            extends AbstractAnimationFunction<T>
    {
        private final float[] keyTimes;
        private final InterpolationMode mode;

        private final T[] values;

        private InterpolatedAnimationFunction(
                long duration,
                T[] values,
                float[] keyTimes,
                InterpolationMode mode)
        {
            super(duration);
            this.values = values;
            this.keyTimes = keyTimes;
            this.mode = mode;
            checkValidKeyTimes(values.length, keyTimes);
        }

        private static void checkValidKeyTimes(int valuesLength, float[] theKeyTimes)
        {
            if (theKeyTimes == null)
            {
                return;
            }

            if (valuesLength < 2 || valuesLength != theKeyTimes.length)
            {
                throw new IllegalArgumentException(
                        "The values and key times arrays must be non-empty and must have equal length.");
            }

            for (int index = 0; index < theKeyTimes.length - 2; index++)
            {
                if (theKeyTimes[index] >= theKeyTimes[index + 1])
                {
                    throw new IllegalArgumentException("The key times must be increasing.");
                }
            }
        }

        @Override
        public T valueAt(long time)
        {
            checkTimeRange(time);
            switch (mode)
            {
            case DISCRETE:
                return discreteValueAt(time);

            case LINEAR:
                return linearValueAt(time);

            default:
                throw new UnsupportedOperationException("Unsupported interpolation mode.");
            }
        }

        private T discreteValueAt(long time)
        {
            return values[indexAt(time, values.length)];
        }

        private int indexAt(long time, int intervalCount)
        {
            long duration = duration();
            // Gleichlange Zeitabschnitte
            if (keyTimes == null)
            {
                return (int) (time * intervalCount / duration);
            }
            for (int index = keyTimes.length - 1; index > 0; index--)
            {
                if (time >= duration * keyTimes[index])
                {
                    return index;
                }
            }
            return 0;
        }

        /**
         * Currently we provide only linear interpolations that are based on floats.
         *
         * @param value1   the first interpolation key point
         * @param value2   the second interpolation key point
         * @param time     the time to get an interpolated value for
         * @param duration the duration of the whole animation
         * @return the interpolated value at the given time
         */
        private T interpolateLinear(
                T value1,
                T value2,
                long time,
                long duration)
        {
            float f1 = ((Number) value1).floatValue();
            float f2 = ((Number) value2).floatValue();
            float value = f1 + (f2 - f1) * time / duration;
            if (value1 instanceof Float)
            {
                return (T) Float.valueOf(value);
            }
            return (T) Integer.valueOf((int) value);
        }

        private T linearValueAt(long time)
        {
            int segments = values.length - 1;
            int beginIndex = indexAt(time, segments);
            int endIndex = beginIndex + 1;
            long lastTime = duration() - 1;
            long beginTime =
                    keyTimes == null
                            ? beginIndex * lastTime / segments
                            : (long) (keyTimes[beginIndex] * lastTime);
            long endTime =
                    keyTimes == null
                            ? endIndex * lastTime / segments
                            : (long) (keyTimes[endIndex] * lastTime);

            return interpolateLinear(
                    values[beginIndex],
                    values[endIndex],
                    time - beginTime,
                    endTime - beginTime);
        }
    }

    /**
     * Produces and returns random Integer values.
     */
    private static final class RandomAnimationFunction
            implements AnimationFunction<Integer>
    {

        private final float changeProbability;
        private final int max;
        private final int min;
        private final Random random;

        private Integer value;

        private RandomAnimationFunction(
                int min,
                int max,
                float changeProbability)
        {
            this.random = new Random();
            this.min = min;
            this.max = max;
            this.changeProbability = changeProbability;
        }

        @Override
        public long duration()
        {
            return Integer.MAX_VALUE;
        }

        @Override
        public Integer valueAt(long time)
        {
            if (value == null
                    || random.nextFloat() < changeProbability)
            {
                value = new Integer(min + random.nextInt(max - min));
            }
            return value;
        }
    }

    /**
     * Helper class used to repeat or sequence an animation function.
     */
    private static final class RepeatedAnimationFunction<T>
            extends AbstractAnimationFunction<T>
    {

        private final AnimationFunction<T> f;
        private final long simpleDuration;

        private RepeatedAnimationFunction(
                AnimationFunction<T> f,
                long repeatTime)
        {
            super(repeatTime);
            this.f = f;
            this.simpleDuration = f.duration();
        }

        @Override
        public T valueAt(long time)
        {
            return f.valueAt(time % simpleDuration);
        }
    }

    /**
     * Helper class for reversing an animation function.
     */
    private static final class ReversedAnimationFunction<T>
            extends AbstractAnimationFunction<T>
    {

        private final AnimationFunction<T> f;

        private ReversedAnimationFunction(AnimationFunction<T> f)
        {
            super(f.duration());
            this.f = f;
        }

        @Override
        public T valueAt(long time)
        {
            return f.valueAt(duration() - time);
        }
    }

    /**
     * Helper class to compose an animation functions from a sequences of such functions.
     */
    private static final class SequencedAnimationFunction<T>
            implements AnimationFunction<T>
    {

        private final List<AnimationFunction<T>> functions;

        private SequencedAnimationFunction(List<AnimationFunction<T>> functions)
        {
            this.functions = Collections.unmodifiableList(functions);
            if (this.functions.isEmpty())
            {
                throw new IllegalArgumentException("The list of functions must not be empty.");
            }
        }

        @Override
        public long duration()
        {
            long cumulatedDuration = 0;
            for (AnimationFunction<T> f : functions)
            {
                cumulatedDuration += f.duration();
            }
            return cumulatedDuration;
        }

        @Override
        public T valueAt(long time)
        {
            if (time < 0)
            {
                throw new IllegalArgumentException("The time must be positive.");
            }

            long begin = 0;
            long end;
            for (AnimationFunction<T> f : functions)
            {
                end = begin + f.duration();
                if (time < end)
                {
                    return f.valueAt(time - begin);
                }
                begin = end;
            }
            throw new IllegalArgumentException("The time must be smaller than the total duration.");
        }
    }

}
