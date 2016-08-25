package com.zte.mos.service;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by olinchy on 16-2-14.
 */
@Entity
public class ReferenceObject implements Serializable
{
    @PrimaryKey
    public String dn;
    public HashSet<String> refBy = new HashSet<String>();
}
