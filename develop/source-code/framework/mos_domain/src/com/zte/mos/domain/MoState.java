package com.zte.mos.domain;

import java.io.Serializable;

/**
 * Created by luoqingkai on 15-8-17.
 */
public class MoState implements Serializable {
    private final String adminState;
    private final String workState;
    private final String linkState;

    public MoState(String adminState, String workState, String linkState) {
        super();
        this.adminState = adminState;
        this.workState = workState;
        this.linkState = linkState;
    }

    public String getWorkState() {
        return workState;
    }

    public String getLinkState() {
        return linkState;
    }

    public boolean equals(Object obj){
        MoState anotherState = (MoState)obj;
        return anotherState.getWorkState().equals(this.workState);
    }
}
