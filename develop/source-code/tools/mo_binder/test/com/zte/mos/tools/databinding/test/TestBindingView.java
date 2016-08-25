package com.zte.mos.tools.databinding.test;

import com.zte.mos.annotation.FieldToAndFro;
import com.zte.mos.message.Mo;
import com.zte.mos.swinglib.forms.builder.DefaultFormBuilder;
import com.zte.mos.swinglib.forms.factories.Borders;
import com.zte.mos.swinglib.forms.layout.FormLayout;
import com.zte.mos.swinglib.validation.view.ValidationResultViewFactory;
import com.zte.mos.tools.databinding.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import static com.zte.mos.tools.databinding.ViewUtilities.Location.Golden;

/**
 * Created by olinchy on 15-8-3.
 */
public class TestBindingView extends JFrame
{
    private final Controller controller;
    private JButton applyButton;
    private BindingComboBox comboBox;
    private BindingCheckBox checkbox;
    private BindingCheckBox exoticCheckbox;
    private HashMap<String, BindingTextField> tfMap = new HashMap<String, BindingTextField>();
    private HashMap<String, Component> rvMap = new HashMap<String, Component>();


    public TestBindingView(Controller controller) throws HeadlessException
    {
        this.controller = controller;

        init();
    }

    public static void main(String[] args)
    {
        Mo mo;
        mo = new Mo("Ne");
        mo.setAttr("name", "abc");
        mo.setAttr("state", "activate");
        // service.get(new DN("/Ems/1/Ne/2"))

        TestBindingView view = new TestBindingView(new TestController(mo));

        ViewUtilities.setLocation(view, Golden);

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }

    private void init()
    {
        DefaultFormBuilder frameBuilder = new DefaultFormBuilder(new FormLayout("pref", "")).border(Borders.DIALOG);

        FormLayout layout = new FormLayout(
                "right:max(40dlu;pref),8dlu,40dlu,8dlu,left:max(40dlu;pref),8dlu,40dlu,8dlu,right:max(40dlu;pref),8dlu,40dlu,8dlu",
                "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout).border(Borders.DIALOG);
        builder.append("Name:", getTextField("name"), 5);
        builder.nextLine();
        builder.append("location:", getTextField("location"), 5);
        builder.nextLine();
        builder.append("latitude:", getTextField("latitude"), 5);

        builder.nextLine();
        builder.append("State:", getComboBox(), 5);
        builder.nextLine();
        builder.append("isNr8000", getCheckbox());
        builder.nextLine();
        builder.append("testExoticCheck", getExoticCheckbox());

        builder.nextLine();
        builder.append("Version:");
        for (String version : new String[]{"2.3.3", "2.4.1", "2.4.2"})
        {
            builder.append(createRadioButton(version));
            builder.append("");
        }

        builder.nextLine();
        builder.append(getResultView("name"), 5);
        builder.nextLine();
        builder.append(getResultView("location"), 5);
        builder.nextLine();
        builder.append(getResultView("latitude"), 5);
        builder.nextLine();
        builder.appendSeparator();

        builder.nextLine();
        builder.append(getApplyButton(), 1);


        frameBuilder.append(builder.getPanel());
        this.getContentPane().add(frameBuilder.getPanel());
        this.setSize(frameBuilder.getPanel().getPreferredSize());
        this.setLocationRelativeTo(null);
    }

    private BindingRadioButton createRadioButton(String version)
    {
        BindingRadioButton rb = new BindingRadioButton(version);
        rb.setText(version);
        rb.bind().at(controller.getModel("model"), "version");
        rb.active();
        return rb;
    }

    private BindingCheckBox getCheckbox()
    {
        if (this.checkbox == null)
        {
            this.checkbox = new BindingCheckBox();
            checkbox.bind().at(controller.getModel("model"), "isNr8000");
            checkbox.active();
        }
        return this.checkbox;
    }

    public JButton getApplyButton()
    {
        if (this.applyButton == null)
        {
            this.applyButton = new JButton();
            this.applyButton.setText("show model");
            this.applyButton.setPreferredSize(new Dimension(120, 30));
            this.applyButton.addActionListener(
                    new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        {
                            controller.commit();
                        }
                    });
        }
        return applyButton;
    }

    public JTextField getTextField(String propertyName)
    {
        if (tfMap.get(propertyName) == null)
        {
            BindingTextField textField = new BindingTextField();
            tfMap.put(propertyName, textField);
            textField.setPreferredSize(new Dimension(400, 30));
            textField.bind().at(this.controller.getModel("model"), propertyName).toAndFro(
                    new FieldToAndFro()
                    {
                        @Override
                        public Object fro(String fieldValue)
                        {
                            return fieldValue;
                        }

                        @Override
                        public Object to(Object value)
                        {
                            return value;
                        }
                    });
            textField.active();
        }
        return tfMap.get(propertyName);
    }

    public JComboBox getComboBox()
    {
        if (comboBox == null)
        {
            comboBox = new BindingComboBox();
            comboBox.setPreferredSize(new Dimension(400, 30));
            comboBox.from(controller.getModel("meta"), "state").at(controller.getModel("model"), "state");

            comboBox.active();

        }
        return comboBox;
    }

    public BindingCheckBox getExoticCheckbox()
    {
        if (exoticCheckbox == null)
        {
            exoticCheckbox = new BindingCheckBox("selected", "deselected");
            exoticCheckbox.bind().at(controller.getModel("model"), "testExotic");
            exoticCheckbox.active();
        }
        return exoticCheckbox;
    }

    public Component getResultView(String propertyName)
    {
        if (rvMap.get(propertyName) == null)
        {
            Component validateView = ValidationResultViewFactory.createReportIconAndTextPane(
                    controller.getModel(
                            "model").getValidateResultModel(propertyName));
            rvMap.put(propertyName, validateView);
        }
        return rvMap.get(propertyName);
    }
}
