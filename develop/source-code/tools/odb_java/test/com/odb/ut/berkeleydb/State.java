package com.odb.ut.berkeleydb;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * Created by olinchy on 15-10-10.
 */
@Entity
public class State
{
    public State(String state, String type)
    {
        this.state = state;
        this.type = type;
    }

    public State()
    {
    }

    @PrimaryKey

    public String personId;
    public String state = "";
    public String type = "";

    @Override
    public int hashCode()
    {
        int result = state != null ? state.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state1 = (State) o;

        if (state != null ? !state.equals(state1.state) : state1.state != null) return false;
        return !(type != null ? !type.equals(state1.type) : state1.type != null);
    }
}
