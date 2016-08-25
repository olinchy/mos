package com.zte.mos.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by olinchy on 15-11-12.
 */
@JsonDeserialize(using = AppendageDeserializer.class)
public interface Appendage
{
    void work(Merger o);

    @JsonProperty("isAppend")
    boolean isAppend();
}
