package com.zte.uep.stub;

import com.zte.app.common.uep.client.service.ShowMsgPaneService;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pavel on 15-9-10.
 */
public class ShowMsgPaneServiceMock implements ShowMsgPaneService{
    private static boolean showFlag = true;
    @Override
    public void showMessageDialog(Component comp, String message, String title, int messageType) {
        if (showFlag) {
            JOptionPane.showMessageDialog(comp, message, title, messageType);
        }
    }

    @Override
    public void showMessageDialog(Component comp, String message, String title, int messageType, String detailMessage) {
        if (showFlag) {
            JOptionPane.showMessageDialog(comp, message, title, messageType);
        }
    }

    public static void setShowFlag(boolean showFlag) {
        ShowMsgPaneServiceMock.showFlag = showFlag;
    }
}
