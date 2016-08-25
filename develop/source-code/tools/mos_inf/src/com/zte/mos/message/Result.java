package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zte.mos.inf.Maybe;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

/**
 * Created by olinchy on 3/17/15 for mosjava.
 */
public abstract class Result<T> implements Serializable
{
    public abstract long getResult();

    @JsonIgnore
    public abstract boolean isSuccess();

    //    @JsonIgnore
    @JsonProperty("ex")
    @JsonInclude(NON_EMPTY)
    public abstract Throwable exception();

    @JsonInclude(NON_EMPTY)
    public abstract List<T> getMo();

    public abstract Maybe<Integer> getTransId();
}
