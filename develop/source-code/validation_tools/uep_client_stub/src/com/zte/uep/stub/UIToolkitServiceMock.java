package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.MWDialogDescPanel;
import com.zte.app.common.uep.client.service.NextBackPanel;
import com.zte.app.common.uep.client.service.OKPanel;
import com.zte.app.common.uep.client.service.UIToolKitService;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by pavel on 15-10-20.
 */
public class UIToolkitServiceMock implements UIToolKitService{
    @Override
    public ImageIcon getIcon(String res) {
        return new ImageIcon("./res/"+res);
    }

    @Override
    public OKPanel createOKCancelPanel(ActionListener okActionListener, ActionListener cancelActionListener) {
        return new OKPanelMock(new BottomBtnPanelMock(okActionListener,cancelActionListener));
    }

    @Override
    public NextBackPanel createNextBackPanel(ActionListener nextActionListener, ActionListener backActionListener)
    {
        return new NextBackPanelMock(new BottomBtnPanelMock(backActionListener,nextActionListener));
    }

    @Override
    public MWDialogDescPanel createDialogDescPanel(String title, String desc, Icon icon) {
        return new MWDialogDescPanel(title, desc, icon);
    }

    @Override
    public void configButtonSize(JButton[] buttons) {

    }
}
