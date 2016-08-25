package com.zte.uep.stub;

import com.zte.ums.uep.api.util.I18n;

/**
 * Created by pavel on 15-8-11.
 */
public class I18nMock extends I18n{
    private I18nMock()
    {

    }
    public String getLabelValue(String labelKey) {
        if (labelKey.contains("_")){
            labelKey = labelKey.substring(labelKey.lastIndexOf("_")+1);
        }
        return labelKey;
    }

}
