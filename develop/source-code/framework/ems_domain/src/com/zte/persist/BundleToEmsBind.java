package com.zte.persist;

import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.PrimaryKey;

/**
 * Created by luoqingkai on 15-7-28.
 */
@Entity
public class BundleToEmsBind {
    @PrimaryKey
    public String bundleId;

    public BundleToEmsBind() {
    }

    public BundleToEmsBind(String bundleId) {
        this.bundleId = bundleId;
    }
}
