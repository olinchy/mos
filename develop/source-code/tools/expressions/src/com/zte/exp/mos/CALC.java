package com.zte.exp.mos;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CALC
{
    public static OPER_CALC ADD = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left + right;
        }
    };
    public static OPER_CALC MINUS = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left - right;
        }
    };
    public static OPER_CALC MULTI = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left * right;
        }
    };
    public static OPER_CALC DIVIDE = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left / right;
        }
    };
    public static OPER_CALC LEFT = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left << right;
        }
    };
    public static OPER_CALC RIGHT = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left >> right;
        }
    };
    public static OPER_CALC BAR = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left | right;
        }
    };
    public static OPER_CALC AMPERSAND = new OPER_CALC()
    {
        @Override
        public long eval(long left, long right)
        {
            return left & right;
        }
    };

    public static OPER_REL_NOT NOT = new OPER_REL_NOT();

    public static OPER_COMPARE BorE = new OPER_COMPARE()
    {

        @Override
        protected boolean eval(Comparable left, Comparable right)
        {
            return left.compareTo(right) >= 0;
        }
    };

    public static OPER_COMPARE BIGGER = new OPER_COMPARE()
    {

        @Override
        protected boolean eval(Comparable left, Comparable right)
        {
            return left.compareTo(right) > 0;
        }
    };

    public static OPER_COMPARE SorE = new OPER_COMPARE()
    {

        @Override
        protected boolean eval(Comparable left, Comparable right)
        {
            return left.compareTo(right) <= 0;
        }
    };

    public static OPER_COMPARE SMALLER = new OPER_COMPARE()
    {

        @Override
        protected boolean eval(Comparable left, Comparable right)
        {
            return left.compareTo(right) < 0;
        }
    };

    public static OPER_REL_BIN NOTEqual = new OPER_REL_BIN()
    {

        @Override
        protected boolean eval(Object left, Object right)
        {
            return !left.equals(right);
        }
    };

    public static OPER_REL_BIN EQUAL = new OPER_REL_BIN()
    {

        @Override
        protected boolean eval(Object left, Object right)
        {
            if (left.getClass() == right.getClass())
            {
                return left.equals(right);
            }
            else
            {
                return String.valueOf(left).equals(String.valueOf(right));
            }
        }
    };
}
