package com.zte.container.ext;

import com.zte.mos.domain.TargetAddress;
import com.zte.mos.msg.framework.CommServiceFactory;
import com.zte.mos.service.ISouthboundService;


class SouthboundService implements ISouthboundService {
    @Override
    public void start() {
        CommServiceFactory.initService();
    }

    @Override
    public void stop() {
        CommServiceFactory.stop();
    }

    @Override
    public void linkOFF(String targetId) {

    }

    @Override
    public void linkON(String targetId) {

    }

    @Override
    public void register(TargetAddress target) {

    }
}
