package com.odb.ut.berkeleydb;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;

/**
 * Created by olinchy on 15-10-10.
 */
@Entity
public class VersionState
{
    @PrimaryKey
    public VersionDescOfNe versionDescOfNe;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String state;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String pkgType;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String neType;
    @SecondaryKey(relate = Relationship.MANY_TO_ONE)
    public String nedn;
}
