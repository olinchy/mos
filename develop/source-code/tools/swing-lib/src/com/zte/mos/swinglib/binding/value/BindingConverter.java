/*
 * Copyright (c) 2012-2013 JGoodies Software GmbH. All Rights Reserved.
 *
 * This software is the proprietary information of JGoodies Software GmbH.
 * Use is subject to license terms.
 *
 */

package com.zte.mos.swinglib.binding.value;

import java.text.Format;

/**
 * Describes an object that converts values from a binding target
 * to a binding source and vice versa.<p>
 * <p/>
 * Used by the binder mechanism to introduce a conversion in the binding chain
 * from source to target. Often the binding source is a bean property
 * that has been turned into a ValueModel. The binding target is typically
 * a user interface component, e.g. combo box or text field.<p>
 * <p/>
 * <strong>Example:</strong><br>
 * <tt>binder.bindBeanProperty("price").<b>converted</b>(currencyConverter).to(priceField);</tt>
 * <p/>
 * <p/>
 * This conversion is similar to the String conversion of the Format class,
 * where {@link Format#format(Object)} equates to {@link #targetValue(Object)},
 * and {@link Format#parseObject(String)} to {@link #sourceValue(Object)}.<p>
 * <p/>
 * Binding converters should be used judiciously; they can be limited
 * w.r.t. to localized formatting conventions. When binding non-String values
 * to a text UI component, consider using a {@link javax.swing.JFormattedTextField}.
 * Formatted text fields provide a powerful means to convert strings to objects
 * and handle many cases that arise around invalid input.
 *
 * @param <S> the source type
 * @param <T> the target type
 * @author Karsten Lentzsch
 * @version $Revision: 1.4 $
 * @since 2.7
 */
public interface BindingConverter<S, T>
{

    /**
     * Returns the converted source value for use by the binding target.
     * <p/>
     * Equates to {@link Format#format(Object)} if the target
     * accepts and provides Strings.
     *
     * @param sourceValue the value to convert
     * @return the converted value
     */
    T targetValue(S sourceValue);

    /**
     * Returns the converted target value for use by the binding source.
     * <p/>
     * Equates to {@link Format#parseObject(String)} if the target
     * accepts and provides Strings.
     *
     * @param targetValue the value to convert
     * @return the converted value
     */
    S sourceValue(T targetValue);

}

