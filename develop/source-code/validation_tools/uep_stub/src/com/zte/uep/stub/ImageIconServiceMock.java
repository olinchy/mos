package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.ImageIconService;

import javax.swing.*;

/**
 * Created by pavel on 15-10-16.
 */
public class ImageIconServiceMock implements ImageIconService{
    @Override
    public ImageIcon getImageIcon(JComponent component, String url) {
        return new ImageIcon();
    }
}
