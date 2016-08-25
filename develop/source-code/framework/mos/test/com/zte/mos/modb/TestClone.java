package com.zte.mos.modb;

import com.zte.mos.modb.stub.CloneChild;
import com.zte.mos.modb.stub.CloneParent;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by olinchy on 8/14/14 for MO_JAVA.
 */
public class TestClone
{
    @Test
    public void test() throws Exception
    {
        CloneParent parent = new CloneParent();
        parent.add(parent.cloneChildGroupOf);
        CloneChild child = new CloneChild();
        parent.cloneChildGroupOf.add(child);
        parent.id = 1;
        parent.value = "1";

        CloneParent clone = (CloneParent) parent.clone();
        assertTrue(clone.id == parent.id);
        assertTrue(clone.value.equals(parent.value));

    }
}
