/*
 * Copyright (c) 2002-2013 JGoodies Software GmbH. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Software GmbH nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.zte.mos.swinglib.binding.value;

import com.zte.mos.swinglib.binding.PresentationModel;
import com.zte.mos.swinglib.binding.adapter.BasicComponentFactory;
import com.zte.mos.swinglib.binding.adapter.Bindings;

/**
 * Adds bound properties for the frequently used JComponent state
 * <em>enabled</em>,<em>visible</em> and JTextComponent state <em>editable</em>
 * to the ValueModel interface.
 * ComponentValueModels can be used to set these properties at the
 * presentation model layer; any ComponentValueModel property change
 * will be reflected by components bound to that ComponentValueModel.<p>
 * <p/>
 * The ComponentValueModel is similar to the Swing Action class.
 * If you disable an Action, all buttons and menu items bound to that Action
 * will be disabled. If you disable a ComponentValueModel, all components
 * bound to that ComponentValueModel will be disabled. If you set the
 * ComponentValueModel to invisible, the component bound to it will become
 * invisible. If you set a ComponentValueModel to non-editable,
 * the JTextComponents bound to it will become non-editable.<p>
 * <p/>
 * Since version 1.1, PresentationModels can vend ComponentValueModels
 * using {@code #getComponentModel(String)} and
 * {@code #getBufferedComponentModel(String)}. Multiple calls
 * to these factory methods return the same ComponentValueModel.<p>
 * <p/>
 * The BasicComponentFactory and the Bindings class check if the ValueModel
 * provided to create/bind a Swing component is a ComponentValueModel.
 * If so, the ComponentValueModel properties will be synchronized
 * with the associated Swing component properties.<p>
 * <p/>
 * It is recommended to use ComponentValueModels only for those models
 * that are bound to view components that require GUI state changes.<p>
 * <p/>
 * <strong>Example Code:</strong><pre>
 * final class AlbumView {
 * <p/>
 *  ...
 * <p/>
 *     private void initComponents() {
 *         // No state modifications required for the name field.
 *         nameField = BasicComponentFactory.createTextField(
 *             presentationModel.getModel(Album.PROPERTY_NAME));
 *         ...
 *         // Enablement shall change for the composer field
 *         composerField = BasicComponentFactory.createTextField(
 *             presentationModel.getComponentModel(Album.PROPERTY_COMPOSER));
 *         ...
 *     }
 * <p/>
 *  ...
 * <p/>
 * }
 * <p/>
 * <p/>
 * public final class AlbumPresentationModel extends PresentationModel {
 * <p/>
 *  ...
 * <p/>
 *     private void updateComposerEnablement(boolean enabled) {
 *         getComponentModel(Album.PROPERTY_COMPOSER).setEnabled(enabled);
 *     }
 * <p/>
 *  ...
 * <p/>
 * }
 * </pre><p>
 * <p/>
 * As of the Binding version 2.0 the ComponentValueModel feature
 * is implemented for text components, radio buttons, check boxes,
 * combo boxes, and lists. JColorChoosers bound using the
 * Bindings class will ignore ComponentValueModel state.
 *
 * @author Karsten Lentzsch
 * @version $Revision: 1.15 $
 * @see PresentationModel#getComponentModel(String)
 * @see PresentationModel#getBufferedComponentModel(String)
 * @see BasicComponentFactory
 * @see Bindings
 * @since 2.4
 */
public interface ComponentValueModel extends ValueModel, ComponentModel
{

    // Just a combined interface

}
