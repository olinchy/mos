package com.zte.uep.stub;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by pavel on 15-9-14.
 */
public class BottomBtnPanelMock extends JPanel {
    public JButton cancelButton = null;
    public JButton okButton = null;
    private ActionListener okListener = null;
    private ActionListener cancelListener = null;

    public BottomBtnPanelMock(ActionListener okListener,ActionListener cancelListener){
        super(new GridBagLayout());
        this.okListener = okListener;
        this.cancelListener = cancelListener;
        initPanel();
    }
    private void initPanel(){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 100;

        okButton = new JButton();
        cancelButton = new JButton();
        okButton.addActionListener(okListener);
        cancelButton.addActionListener(cancelListener);
        cancelButton.setText("Cancelxx");
        okButton.setText("Nextxx");
        okButton.setPreferredSize(new Dimension(90, 23));
        cancelButton.setPreferredSize(new Dimension(90, 23));

        this.add(okButton, constraints);
        this.add(cancelButton, constraints);
    }

}