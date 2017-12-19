package com.olch.library.presentation.windows;

import com.olch.library.persist.entities.Book;
import com.vaadin.ui.*;

/**
 * Created by Ольга on 16.12.2017.
 */
public abstract class AbstractDialogWindow<T> extends Window {

    protected VerticalLayout fieldsLayout = new VerticalLayout();

    public AbstractDialogWindow() {
        super("window"); // Set window caption
        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        content.setMargin(true);
        // Disable the close button

        setStyleWindow();
        // Trivial logic for closing the sub-window
        Button ok = new Button("OK");
        Button cancel = new Button("CANCEL");
        ok.addClickListener(e -> save());
        cancel.addClickListener(e -> cancel());
        content.addComponents(fieldsLayout, ok, cancel);
        setContent(content);

    }

    public abstract void setEntity(T entity);

    private void setStyleWindow() {
        setSizeUndefined();
        center();
        setResizable(false);
        setClosable(false);
    }

    protected abstract void save();

    protected abstract void cancel();

}
