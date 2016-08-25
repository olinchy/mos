package com.zte.mos.domain;

public interface TargetAddress {

    String getTargetID();

    String getIpAddress();

    IMosHead getMosHead();
    ModelHead getModelHead();

    ImagedSystem getSystem();
}
