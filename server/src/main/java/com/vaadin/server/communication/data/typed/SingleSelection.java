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
package com.vaadin.server.communication.data.typed;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.vaadin.event.handler.Handler;
import com.vaadin.event.handler.Registration;
import com.vaadin.server.communication.data.typed.SelectionModel.Single;
import com.vaadin.shared.data.selection.SelectionServerRpc;

/**
 * {@link SelectionModel} for selecting a single value.
 * 
 * @param <T>
 *            type of selected data
 */
public class SingleSelection<T> extends AbstractSelectionModel<T> implements
        Single<T> {

    private final LinkedHashSet<Handler<T>> handlers = new LinkedHashSet<>();

    public SingleSelection() {
        registerRpc(new SelectionServerRpc() {

            @Override
            public void select(String key) {
                setValue(getData(key), true);
            }

            @Override
            public void deselect(String key) {
                if (getData(key).equals(value)) {
                    setValue(null, true);
                }
            }
        });
    }

    private T value = null;

    @Override
    public Collection<T> getSelected() {
        if (value != null) {
            return Collections.singleton(value);
        } else {
            return Collections.emptySet();
        }
    }

    @Override
    public void setValue(T value) {
        setValue(value, false);
    }

    protected void setValue(T value, boolean userOriginated) {
        if (this.value != value) {
            if (this.value != null) {
                refresh(this.value);
            }
            this.value = value;
            if (value != null) {
                refresh(value);
            }
            Set<Handler<T>> copy = new LinkedHashSet<>(handlers);
            for (Handler<T> handler : copy) {
                handler.handleEvent(new com.vaadin.event.handler.Event<T>(this
                        .getParent(), value, userOriginated));
            }
        }
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Registration onChange(Handler<T> handler) {
        if (handler == null) {
            throw new IllegalArgumentException("Handler can't be null");
        }
        handlers.add(handler);
        return () -> handlers.remove(handler);
    }

    @Override
    public void select(T value) {
        if (this.value != value) {
            setValue(value);
        }
    }

    @Override
    public void deselect(T value) {
        if (this.value == value) {
            setValue(null);
        }
    }

    @Override
    public void remove() {
        if (getValue() != null) {
            refresh(getValue());
        }

        super.remove();
    }
}
