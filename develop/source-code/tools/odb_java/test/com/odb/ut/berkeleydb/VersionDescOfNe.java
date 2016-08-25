package com.odb.ut.berkeleydb;

import com.sleepycat.persist.model.KeyField;
import com.sleepycat.persist.model.Persistent;

/**
 * Created by olinchy on 15-10-10.
 */
@Persistent
public class VersionDescOfNe
{
    @KeyField(1)
    public String nedn;
    @KeyField(2)
    public String versionNo;
}
