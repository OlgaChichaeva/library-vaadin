package com.olch.library.presentation.views;

import com.olch.library.persist.entities.Author;
import com.olch.library.presentation.windows.AbstractDialogWindow;
import com.olch.library.presentation.windows.AuthorDialogWindow;
import com.olch.library.services.AuthorsService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.List;

/**
 * Created by Ольга on 16.12.2017.
 */
public class AuthorsView extends AbstractView {

    private final AuthorsService authorsService;

    public AuthorsView(HorizontalLayout navigationMenu, AuthorsService authorsService) {
        super(navigationMenu);
        this.authorsService = authorsService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
        Notification.show("Welcome to Authors");
        Page.getCurrent().setTitle("Authors");
        refresh();
        addComponent(new VerticalLayout(navigationMenu, actions, grid));
    }

    @Override
    public void refresh() {
        List<Author> authors = authorsService.getAllAuthors();
        grid.setContainerDataSource(new BeanItemContainer<>(Author.class, authors));
    }

    @Override
    protected void add() {
        showEditorWindow(new Author());
    }

    @Override
    protected void edit() {
        Author row = (Author) grid.getSelectedRow();
        if (row != null) {
            showEditorWindow(row);
        }
    }

    @Override
    protected void delete() {
        Author row = (Author) grid.getSelectedRow();
        if (row != null) {
            authorsService.deleteAuthor(row.getIdAuthor());
            refresh();
        }
    }

    private void showEditorWindow(Author author) {
        AbstractDialogWindow authorWindow = new AuthorDialogWindow(this, authorsService);
        authorWindow.setEntity(author);
        UI.getCurrent().addWindow(authorWindow);
    }
}
