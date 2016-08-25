package com.zte.mos.type;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.BitSet;
import java.util.Stack;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Created by olinchy on 15-10-14.
 */
@JsonSerialize(using = RangeSerializer.class)
@JsonDeserialize(using = RangeDeserializer.class)
public class Range implements Serializable, Merger<Range>
{
    private static int OFFSET = Short.MAX_VALUE;
    public Range(RangeExp range)
    {
        this(range.toArray());
    }

    public Range(String... sectionOrIndexes)
    {
        add(sectionOrIndexes);
    }

    public Range add(String... sectionOrIndexes)
    {
        batchSet(sectionOrIndexes, true);
        return this;
    }

    private void batchSet(String[] sectionOrIndexes, boolean mark)
    {
        for (String sectionOrIndex : sectionOrIndexes)
        {
            set(sectionOrIndex, mark);
        }
    }

    private void set(String sectionOrIndex, boolean mark)
    {
        String[] fromTo = preHandle(sectionOrIndex);
        if (fromTo.length == 2)
        {
            bits.set(OFFSET + parseInt(fromTo[0]), OFFSET + parseInt(fromTo[1]) + 1, mark);
        }
        else if (fromTo.length == 1)
        {
            bits.set(OFFSET + parseInt(sectionOrIndex), mark);
        }
    }

    private String[] preHandle(String sectionOrIndex)
    {
        int[] groupIds = new int[]{2, 5, 8};
        Pattern pattern = Pattern.compile("(((-[\\d]+)|([\\d]+))-((-[\\d]+)|([\\d]+)))|((-[\\d]+)|([\\d]+))");
        Matcher matcher = pattern.matcher(sectionOrIndex);
        if (matcher.find())
        {
            Vector<String> vector = new Vector<String>();
            for (int i = 0; i < groupIds.length; i++)
            {
                String groupValue = matcher.group(groupIds[i]);
                if (groupValue != null)
                {
                    vector.add(groupValue);
                }
            }
            return vector.toArray(new String[vector.size()]);
        }

        return new String[0];
    }

    private BitSet bits = new BitSet();

    public boolean contains(String... sectionOrIndexes)
    {
        for (String sectionOrIndex : sectionOrIndexes)
        {
            String[] arrayFT = preHandle(sectionOrIndex);
            if (arrayFT.length == 2)
            {
                // XX-XX
                for (int i = parseInt(arrayFT[0]); i < parseInt(arrayFT[1]) + 1; i++)
                {
                    if (!bits.get(i + OFFSET))
                        return false;
                }
            }
            else if (arrayFT.length == 1)
            {
                if (!bits.get(parseInt(sectionOrIndex) + OFFSET))
                    return false;
            }
        }
        return true;
    }

    public boolean contains(Range range)
    {
        return this.contains(new RangeExp(range.toString()).toArray());
    }

    public Range del(String... sectionOrIndexes)
    {
        batchSet(sectionOrIndexes, false);
        return this;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder("[");
        Stack<Pair<Integer, Integer>> numStack = new Stack<Pair<Integer, Integer>>();

        for (int i = 0; i < bits.length(); i++)
        {
            if (bits.get(i))
            {
                int num = i - OFFSET;
                if (numStack.isEmpty())
                {
                    numStack.push(Pair.pair(num, num));
                }
                else
                {
                    Pair<Integer, Integer> last = numStack.pop();
                    if (num == last.second() + 1)
                    {
                        last.setSecond(num);
                        numStack.push(last);
                    }
                    else
                    {
                        numStack.push(last);
                        numStack.push(Pair.pair(num, num));
                    }
                }
            }
        }

        for (Pair<Integer, Integer> pair : numStack)
        {
            builder.append(pair.toString()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.length() > 0 ? builder.append("]").toString() : "";
    }

    public long[] toArray()
    {
        String[] digits = bits.toString().replaceAll("\\{(.*)\\}", "$1").replace(" ", "").split(",");
        long[] longDigits = new long[digits.length];
        for (int i = 0; i < digits.length; i++)
        {
            String digit = digits[i];
            longDigits[i] = Long.parseLong(digit) - OFFSET;
        }

        return longDigits;
    }

    public void add(Range range)
    {
        this.bits.or(range.bits);
    }

    public void del(Range range)
    {
        this.bits.andNot(range.bits);
    }

    public boolean intersects(Range range)
    {
        return this.bits.intersects(range.bits);
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Range))
        {
            return false;
        }
        return this.bits.equals(((Range)o).bits);
    }

}
