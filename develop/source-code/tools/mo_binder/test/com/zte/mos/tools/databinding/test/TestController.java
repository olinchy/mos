package com.zte.mos.tools.databinding.test;

import com.zte.mos.message.Mo;
import com.zte.mos.message.MoMetaClass;
import com.zte.mos.swinglib.validation.ValidationResult;
import com.zte.mos.tools.databinding.Bean;
import com.zte.mos.tools.databinding.Controller;
import com.zte.mos.tools.databinding.MoBean;
import com.zte.mos.tools.databinding.Validator;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.zte.mos.type.Pair.pair;

/**
 * Created by olinchy on 15-8-6.
 */
public class TestController implements Controller
{
    HashMap<String, Bean> models = new HashMap<String, Bean>();

    public TestController(Mo mo)
    {
        MoMetaClass meta = new MoMetaClass();
        meta.enums.put(
                "state", new ArrayList<String>(
                        Arrays.asList(
                                new String[]{"activate", "deactivate", "unknown"})));
        models.put(
                "model", new MoBean(
                        mo, pair("name", new NameValidator()), pair("location", new LocationValidator()), pair(
                        "latitude", new LatitudeValidator())));
        models.put("meta", new MetaEnumsBean(meta));
    }

    @Override
    public Bean getModel(String modelName)
    {
        return models.get(modelName);
    }

    @Override
    public void commit()
    {
        if (models.get("model").hasError())
        {
            JOptionPane.showMessageDialog(null, "can not commit when error occurred!");
        }
        else
            JOptionPane.showMessageDialog(null, models.get("model").toString());
    }

    private class NameValidator implements Validator
    {
        protected String propertyName = "name";

        @Override
        public ValidationResult validate(Object value)
        {
            ValidationResult result = new ValidationResult();
            if (String.valueOf(value).matches("[0-9]+"))
                result.addWarning(propertyName + " is not suppose to be a number", value);
            return result;
        }
    }

    private class LocationValidator extends NameValidator
    {
        public LocationValidator()
        {
            this.propertyName = "location";
        }
    }

    private class LatitudeValidator implements Validator
    {
        @Override
        public ValidationResult validate(Object value)
        {
            ValidationResult result = new ValidationResult();
            if (String.valueOf(value).matches("[0-9]+\\.[0-9]"))
            {
                double doubleValue = Double.valueOf(String.valueOf(value));
                if (doubleValue < 0 || doubleValue > 999.0)
                {
                    result.addError("latitude should between 0-999.0");
                }
            }
            else
            {
                result.addError("latitude should be a number between 0.0-999.0");
            }

            return result;
        }
    }
}
