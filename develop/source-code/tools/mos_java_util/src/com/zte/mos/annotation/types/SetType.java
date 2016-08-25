package com.zte.mos.annotation.types;

import com.fasterxml.jackson.databind.JsonNode;
import com.zte.mos.annotation.FieldToAndFroWithValidator;
import com.zte.mos.annotation.Validator;
import com.zte.mos.annotation.Validators;
import com.zte.mos.util.tools.JsonUtil;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 15-5-14.
 */
public class SetType implements FieldToAndFroWithValidator
{
    @Override
    public Object fro(String fieldValue)
    {
        if (fieldValue == null || fieldValue.equalsIgnoreCase("") || fieldValue.equalsIgnoreCase("null"))
            return null;
        return fieldValue;
    }

    @Override
    public Object to(Object value)
    {
        if (value == null)
        {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        try
        {
            JsonNode valueNode = JsonUtil.toNode(value);
            if (valueNode.elements().hasNext())
            {
                for (JsonNode ele : valueNode)
                {
                    builder.append(ele.asText()).append(",");
                }
                builder.deleteCharAt(builder.length() - 1);
            }
        }
        catch (Exception e)
        {
            logger(SetType.class).warn("set.to caught exception ", e);
        }

        return builder.toString();
    }

    @Override
    public Validator validator()
    {
        return Validators.Set;
    }
}
