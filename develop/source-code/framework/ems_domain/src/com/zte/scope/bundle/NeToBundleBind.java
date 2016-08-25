package com.zte.scope.bundle;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * Created by luoqingkai on 15-7-28.
 */
@Entity
public class NeToBundleBind {
    public String bundleId;
    @PrimaryKey
    public String neDN;

    public NeToBundleBind() {
    }

    public NeToBundleBind(String bundleId, String neDN) {
        this.bundleId = bundleId;
        this.neDN = neDN;
    }
}
