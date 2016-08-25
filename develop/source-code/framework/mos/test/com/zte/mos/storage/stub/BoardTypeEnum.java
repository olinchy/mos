package com.zte.mos.storage.stub;

import com.zte.mos.annotation.MoEnum;

public enum BoardTypeEnum implements MoEnum<Integer>
{
    rmud(37379), rtuhe(37127), rtuho(37159), rtue(37124), rmea(37618), rtua(37120), rcuma(
        37617), rtuc(37122), rtub(37121), rmuh(37383), rtud(37123), rmuc(37378), rtea(37362), rmue(
        37380), rmuf(
        37381), rteb(37363);
    private Integer enumValue;

    BoardTypeEnum(Integer enumValue)
    {
        this.enumValue = enumValue;
    }

    @Override
    public String value()
    {
        return String.valueOf(enumValue);
    }

    @Override
    public MoEnum valuesOf(String name)
    {
        return valueOf(name);
    }

    @Override
    public MoEnum contentOf(Integer content)
    {
        return null;
    }

}
