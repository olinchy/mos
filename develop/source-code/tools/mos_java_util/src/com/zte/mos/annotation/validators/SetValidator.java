package com.zte.mos.annotation.validators;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.annotation.MoAttribute;
import com.zte.mos.annotation.ValidateException;

import static com.zte.mos.util.tools.JsonUtil.toNode;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public class SetValidator extends BasicValidator
{
    @Override
    protected void validateData(MoAttribute attr, Object value) throws ValidateException
    {
        try
        {
            ArrayNode node = (ArrayNode) toNode(value);
        }
        catch (Exception e)
        {
            throw new ValidateException("field " + attr.field() + " check as Array failed!");
        }
    }
}
