package com.zte.mos.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zte.mos.type.Range;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by olinchy on 3/19/15 for mosjava.
 */
public class AttributeClass implements Serializable
{
    // attrId":0,"maxLength":30,"name":"creator","mutable":true,"type":"String
    public int attrId;
    public int maxLength;
    public String name;
    public boolean mutable;
    public String type;
    @JsonProperty("enum")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public LinkedHashMap<String, Object> enums = new LinkedHashMap<String, Object>();
    @JsonDeserialize(using = LongDeserializerWith0x.class)
    public Long minimum;
    @JsonDeserialize(using = LongDeserializerWith0x.class)
    public Long maximum;
    @JsonProperty("default")
    public Object defaultValue;
    public Range range;
}
