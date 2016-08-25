package com.zte.mos.annotation;

import com.zte.mos.annotation.types.*;

public enum Type implements FieldToAndFroWithValidator
{
    UserDefine(new UserDefineType()),
    WORD16(new Word16Type()),
    SWORD16(new Word16Type()),
    WORD32(new Word32Type()),
    SWORD32(new Word32Type()),
    BYTE(new ByteType()),
    String(new StringType()), // varchar
    Sequence(new SequenceType()), // fixed-length array
    Set(new SetType()),
    Date(new DateType()), // A value set.
    IPv4Addr(new IPV4Type()),
    ENUM(new EnumType()),
    CHAR(new CharType()),
    MacAddr(new MacAddrType()),
    bool(new BooleanType()),
    Range(new RangeType()), WORD64(new Word64Type()), FLOAT(new FloatType()), DOUBLE(new DoubleType());
    private final FieldToAndFroWithValidator f;

    Type(FieldToAndFroWithValidator f)
    {
        this.f = f;
    }

    @Override
    public Object fro(String fieldValue)
    {
        return f.fro(fieldValue);
    }

    @Override
    public Object to(Object value)
    {
        return f.to(value);
    }

    @Override
    public Validator validator()
    {
        return f.validator();
    }
}
