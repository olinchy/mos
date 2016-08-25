package com.odb.ut.berkeleydb;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

import java.util.ArrayList;

/**
 * Created by olinchy on 15-10-10.
 */
@Entity
public class VersionOfNe
{
    @PrimaryKey
    public String neDN;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String neType;
    public ArrayList<VersionState> versionStates = new ArrayList<VersionState>();
}
