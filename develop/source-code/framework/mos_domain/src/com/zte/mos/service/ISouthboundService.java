package com.zte.mos.service;


import com.zte.mos.domain.TargetAddress;

@DomainUnique
public interface ISouthboundService extends IExtService{

    void linkOFF(String targetId);
    void linkON(String targetId);
    void register(TargetAddress target);
}
