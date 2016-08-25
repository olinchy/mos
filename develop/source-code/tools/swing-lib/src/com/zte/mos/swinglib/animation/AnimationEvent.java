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
 * An immutable object that describes events appropriate for animations
 * with the source animation, the type (started, stopped), and the time
 * the event has happened.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.8 $
 * @see AnimationListener
 */
public final class AnimationEvent
{

    /**
     * The animation that has been started or stopped.
     */
    private final Animation source;

    /**
     * Describes the state change of the animation: started or stopped.
     */
    private final Type type;

    /**
     * Describes when the event has been created.
     */
    private final long time;

    // Instance Creation ******************************************************

    /**
     * Constructs an {@code AnimationEvent} for the
     * initiating animation, event type, and time.
     *
     * @param source the {@code Animation} that has originated the event
     * @param type   the event type: start or stop
     * @param time   the time the event has happened, which is likely
     *               before the event has been created
     */
    AnimationEvent(Animation source, Type type, long time)
    {
        this.source = source;
        this.type = type;
        this.time = time;
    }

    // ************************************************************************

    /**
     * Returns the animation the has originated this event.
     *
     * @return the animation that has originated this event
     */
    public Animation getSource()
    {
        return source;
    }

    /**
     * Returns the event type: started or stopped.
     *
     * @return the event type: started or stopped
     */
    public Type type()
    {
        return type;
    }

    /**
     * Returns the time when this event has happened in the
     * time interval of this event's source animation.
     * This time may differ from the time the event has been created.
     *
     * @return the time the event has happened
     */
    public long time()
    {
        return time;
    }

    /**
     * Returns an appropriate string representation.
     *
     * @return a string representation for this event
     */
    @Override
    public String toString()
    {
        return "[type= "
                + type
                + "; time= "
                + time
                + "; source="
                + source
                + ']';
    }

    /**
     * Enumerates the animation event types: started and stopped.
     */
    public enum Type
    {

        STARTED("started"),
        STOPPED("stopped");

        private final String name;

        Type(String name)
        {
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name;
        }

    }

}
