package com.zte.scope.bundle;

import com.zte.concept.MIB;
import com.zte.scope.common.NeModelMib;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by luoqingkai on 15-7-15.
 */
public class BundleMIB implements MIB {
    public static final BundleMIB instance = new BundleMIB();

    private Set<String> mib = new HashSet<String>();

    private BundleMIB(){
        for (String oneMib : NeModelMib.mibs){
            mib.add(oneMib);
        }
    }
    @Override
    public boolean contains(String oneMib) {
        return mib.contains(oneMib);
    }
}
