package com.zte.concept;

/**
 * Created by luoqingkai on 15-8-5.
 */
public enum DomainState {
    Empty(0), Created(1), Updated(2), Deleted(3), Committed(4), Replaced(5);

    public final int value;

    DomainState(int v){
        value = v;
    }
}
