/*
 * Copyright 2000-2014 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.client.connectors.data;

import com.vaadin.client.data.selection.SelectionModel;

/**
 * Marker interface for Connectors that have a SelectionModel.
 */
public interface HasSelection extends HasDataSource {

    /**
     * Sets the selection model for this Connector.
     * 
     * @param selectionModel
     *            selection model
     */
    public void setSelectionModel(SelectionModel selectionModel);

    /**
     * Gets the current selection model for this Connector.
     * 
     * @return selection model
     */
    public SelectionModel getSelectionModel();
}
