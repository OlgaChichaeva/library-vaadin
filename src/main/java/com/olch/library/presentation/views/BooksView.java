package com.olch.library.presentation.views;

import com.olch.library.persist.dao.hsqldb.filters.BooksFilter;
import com.olch.library.persist.entities.Book;
import com.olch.library.presentation.windows.AbstractDialogWindow;
import com.olch.library.presentation.windows.BookDialogWindow;
import com.olch.library.services.AuthorsService;
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
public class BooksView extends AbstractView {

    private TextField authorFilter = new TextField("Surname");
    private TextField publisherFilter = new TextField("Publisher");
    private TextField titleFilter = new TextField("Title");

    private final BooksService booksService;
    private final AuthorsService authorsService;
    private final GenresService genresService;

    public BooksView(HorizontalLayout navigationMenu, BooksService booksService, GenresService genresService, AuthorsService authorsService) {
        super(navigationMenu);
        this.booksService = booksService;
        this.genresService = genresService;
        this.authorsService = authorsService;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        super.enter(viewChangeEvent);
        Notification.show("Welcome to Books");
        Page.getCurrent().setTitle("Books");

        refresh();

        addComponents(navigationMenu, actions, createBooksFilter(), grid);
    }

    @Override
    public void refresh() {
        showBooks(booksService.getAllBooks());
    }

    protected HorizontalLayout createBooksFilter() {
        HorizontalLayout filterLayout = new HorizontalLayout();
        Button apply = new Button("APPLY");
        apply.addClickListener(e -> applyFilter());
        filterLayout.addComponents(authorFilter, publisherFilter, titleFilter, apply);
        return filterLayout;
    }

    protected void applyFilter() {
        BooksFilter booksFilter = new BooksFilter(authorFilter.getValue(), publisherFilter.getValue(), titleFilter.getValue());
        List<Book> filterBooks = booksService.getAllBooksByFilter(booksFilter);
        showBooks(filterBooks);
    }

    @Override
    protected void add() {
        showEditorWindow(new Book());
    }

    @Override
    protected void edit() {
        Book row = (Book) grid.getSelectedRow();
        if (row != null) {
            showEditorWindow(row);
        }
    }

    @Override
    protected void delete() {
        Book row = (Book) grid.getSelectedRow();
        if (row != null) {
            booksService.deleteBook(row.getIdBook());
            refresh();
        }
    }

    private void showBooks(List<Book> books) {
        grid.setContainerDataSource(new BeanItemContainer<>(Book.class, books));
    }

    private void showEditorWindow(Book book) {
        AbstractDialogWindow bookWindow = new BookDialogWindow(authorsService, genresService, booksService, this);
        bookWindow.setEntity(book);
        UI.getCurrent().addWindow(bookWindow);
    }

}
