package com.zte.mos.annotation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.zte.mos.annotation.validators.EnumValidator;
import com.zte.mos.annotation.validators.NumberValidator;
import com.zte.mos.annotation.validators.SetValidator;
import com.zte.mos.annotation.validators.StringValidator;

import static com.zte.mos.util.tools.JsonUtil.toNode;

/**
 * Created by olinchy on 2/16/15 for mosjava.
 */
public enum Validators implements Validator
{
    Float(
            new NumberValidator()
            {
                @Override
                protected void validateData(MoAttribute attr, Object value) throws ValidateException
                {
                    Double doubleValue = Double.valueOf(String.valueOf(value));
                    check(attr, doubleValue);
                }
            }),
    Number(new NumberValidator()),
    STRING(new StringValidator()),
    Set(new SetValidator()),
    ENUM(new EnumValidator()),
    Nothing(
            new Validator()
            {
                @Override
                public void validate(MoAttribute attr, Object value, boolean isSet) throws ValidateException
                {
                }
            }),
    Seq(
            new Validator()
            {
                @Override
                public void validate(MoAttribute attr, Object value, boolean isSet) throws ValidateException
                {
                    try
                    {
                        ArrayNode node = (ArrayNode) toNode(value);
                    }
                    catch (Exception e)
                    {
                        throw new ValidateException("field " + attr.field() + " check as List failed!");
                    }
                }
            });
    private final Validator validator;

    Validators(Validator validator)
    {
        this.validator = validator;
    }

    @Override
    public void validate(MoAttribute attr, Object value, boolean isSet) throws ValidateException
    {
        this.validator.validate(attr, value, isSet);
    }
}
