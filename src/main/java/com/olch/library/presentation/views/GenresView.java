package com.olch.library.presentation.views;

import com.olch.library.persist.entities.Genre;
import com.olch.library.presentation.windows.AbstractDialogWindow;
import com.olch.library.presentation.windows.GenreDialogWindow;
import com.olch.library.services.BooksService;
import com.olch.library.services.GenresService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.ui.*;

import java.util.List;

/**
 * Created by Ольга on 16.12.2017.
 */
public class GenresView extends AbstractView {

    private GenresService genresService;
    private BooksService booksService;

    public GenresView(HorizontalLayout navigationMenu, GenresService genresService, BooksService booksService) {
        super(navigationMenu);
        this.genresService = genresService;
        this.booksService = booksService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
        Notification.show("Welcome to Genres");
        Page.getCurrent().setTitle("Genres");
        refresh();
        addComponent(new VerticalLayout(navigationMenu, actions, createStatistics(), grid));
    }

    @Override
    public void refresh() {
        List<Genre> genres = genresService.getAllGenres();
        grid.setContainerDataSource(new BeanItemContainer<>(Genre.class, genres));
    }

    @Override
    protected void add() {
        showEditorWindow(new Genre());
    }

    @Override
    protected void edit() {
        Genre row = (Genre) grid.getSelectedRow();
        if (row != null) {
            showEditorWindow(row);
        }
    }

    @Override
    protected void delete() {
        Genre row = (Genre) grid.getSelectedRow();
        if (row != null) {
            genresService.deleteGenre(row.getIdGenre());
            refresh();
        }
    }

    private Button createStatistics() {
        Button statistics = new Button("Show statistics");
        statistics.addClickListener(e -> showStatistics());
        return statistics;
    }

    private void showStatistics() {
        Genre row = (Genre) grid.getSelectedRow();
        if (row != null) {
            int countBook = booksService.getBooksCountForGenre(row.getIdGenre());
            Notification.show("Count books for genre:" + countBook);
        }

    }

    private void showEditorWindow(Genre genre) {
        AbstractDialogWindow genreWindow = new GenreDialogWindow(genresService,this );
        genreWindow.setEntity(genre);
        UI.getCurrent().addWindow(genreWindow);
    }

}