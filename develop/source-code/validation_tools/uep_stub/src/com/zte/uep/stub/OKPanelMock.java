package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.OKPanel;

import javax.swing.*;

/**
 * Created by pavel on 15-9-14.
 */
public class OKPanelMock extends OKPanel{
    private BottomBtnPanelMock panel = null;
    public OKPanelMock(JPanel panel) {
        super(panel);
        this.panel = (BottomBtnPanelMock)panel;
    }

    @Override
    public JButton getOKButton() {
        return panel.okButton;
    }

    @Override
    public JButton getCancelButton() {
        return panel.cancelButton;
    }


}
