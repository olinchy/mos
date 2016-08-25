package com.zte.scope.ems;

import com.zte.concept.MIB;
import com.zte.scope.common.EmsModelMib;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by luoqingkai on 15-7-14.
 */
public class EmsMIB implements MIB {

    public static final EmsMIB instance = new EmsMIB();

    private Set<String> mib = new HashSet<String>();

    private EmsMIB(){
        mib.add("/");
        mib.add("/Ems/Bundle");
        for (String oneMib : EmsModelMib.mibs){
            mib.add(oneMib);
        }
    }
    @Override
    public boolean contains(String oneMib) {
        return mib.contains(oneMib);
    }
}
