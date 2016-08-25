package com.zte.concept;

/**
 * Created by luoqingkai on 15-8-5.
 */
public enum DomainOperation {

    rollback(0), create(1), update(2), delete(3), commit(4);

    public final int value;

    DomainOperation(int v){
        this.value = v;
    }
}
