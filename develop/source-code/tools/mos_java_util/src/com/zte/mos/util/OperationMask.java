package com.zte.mos.util;

public class OperationMask
{
    public static final OperationMask configChecking = new OperationMask(
            convert(1));
    public static final OperationMask configIndication = new OperationMask(
            convert(2));
    public static final OperationMask config = configChecking
            .or(configIndication);
    public static final OperationMask get = new OperationMask(convert(3));
    public static final OperationMask action = new OperationMask(convert(4));
    public static final OperationMask event = new OperationMask(convert(5), false);
    public static final OperationMask none = new OperationMask(0);
    public static final OperationMask all = new OperationMask(0xffffffff);
    public static OperationMask ref = new OperationMask(convert(6));
    private final int mask;

    public OperationMask(int mask)
    {
        this(mask, true);
    }

    public OperationMask(int mask, boolean isRpc)
    {
        this.mask = mask;
        this.isRpc = isRpc;
    }

    private final boolean isRpc;

    private static short convert(int bits)
    {
        return (short) (1 << bits);
    }

    public int mask()
    {
        return mask;
    }

    public boolean isRpc()
    {
        return isRpc;
    }

    public OperationMask or(OperationMask other)
    {
        return new OperationMask(mask | other.mask());
    }

    public boolean isSet(OperationMask other)
    {
        return ((mask & other.mask()) == other.mask());
    }

    @Override
    public String toString()
    {
        return String.valueOf(mask());
    }
}
