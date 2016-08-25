package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.I18nService;
import com.zte.mos.exception.MOSException;
import com.zte.mos.util.Singleton;
import com.zte.ums.uep.api.util.I18n;

/**
 * Created by pavel on 15-9-17.
 */
public class I18nServiceMock implements I18nService{
    @Override
    public I18n getI18n(String fileName) {
        try {
            return Singleton.getInstance(I18nMock.class);
        } catch (MOSException e) {
            e.printStackTrace();
            throw new RuntimeException("Can not get I18n by "+fileName);
        }
    }
}
