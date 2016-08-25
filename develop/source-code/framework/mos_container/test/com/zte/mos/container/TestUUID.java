package com.zte.mos.container;

import com.zte.mos.domain.NeIdentity;
import com.zte.mos.exception.MOSException;
import org.junit.Assert;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.UUID;

/**
 * Created by ccy on 7/1/16.
 */
public class TestUUID
{
    private UUID key = null;
    @Test
    public void should_equal_uuid()
    {
        UUID id = generate();
        Assert.assertTrue(key.equals(id));
    }

    @Test
    public void should_none_equal_uuid()
    {
        UUID id1 = generate();
        UUID id2 = generate();
        Assert.assertFalse(id1.equals(id2));

    }

    private UUID generate()
    {
        return key = UUID.randomUUID();
    }



}
