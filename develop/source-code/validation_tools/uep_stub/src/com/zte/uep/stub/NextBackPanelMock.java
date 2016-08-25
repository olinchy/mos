package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.NextBackPanel;

import javax.swing.*;

/**
 * Created by pavel on 16-2-5.
 */
public class NextBackPanelMock extends NextBackPanel
{
    private BottomBtnPanelMock panel = null;
    public NextBackPanelMock(JPanel panel) {
        super(panel);
        this.panel = (BottomBtnPanelMock)panel;
    }

    @Override
    public JButton getNextButton() {
        return panel.cancelButton;
    }

    @Override
    public JButton getBackButton() {
        return panel.okButton;
    }

}
