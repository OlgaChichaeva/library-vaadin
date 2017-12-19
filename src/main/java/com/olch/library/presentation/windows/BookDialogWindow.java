package com.olch.library.presentation.windows;

import com.olch.library.persist.entities.Author;
import com.olch.library.persist.entities.Book;
import com.olch.library.persist.entities.Genre;
import com.olch.library.presentation.views.AbstractView;
import com.olch.library.services.AuthorsService;
import com.olch.library.services.BooksService;
import com.olch.library.services.GenresService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Ольга on 16.12.2017.
 */
@Slf4j
public class BookDialogWindow extends AbstractDialogWindow<Book> {

    private static final String POJO_TITLE = "title";
    private static final String POJO_PUBLISHER = "publisher";
    private static final String POJO_YEAR_OF_ISSUE = "yearOfIssue";
    private static final String POJO_CITY = "city";
    private static final String POJO_SURNAME = "surname";
    private static final String POJO_AUTHOR = "author";
    private static final String POJO_GENRE = "genre";

    private final GenresService genresService;
    private final AuthorsService authorsService;
    private final BooksService booksService;
    private final AbstractView abstractView;
    private Book book;
    private TextField title;
    private NativeSelect author;
    private NativeSelect genre;
    private TextField publisher;
    private TextField yearOfIssue;
    private TextField city;
    private final BeanFieldGroup<Book> binder = new BeanFieldGroup<>(Book.class);

    public BookDialogWindow(AuthorsService authorsService, GenresService genresService, BooksService booksService, AbstractView abstractView) {
        super();
        this.authorsService = authorsService;
        this.genresService = genresService;
        this.booksService = booksService;
        this.abstractView = abstractView;
    }

    @Override
    public void setEntity(Book book) {
        this.book = book;

        binder.setItemDataSource(book);
        binder.setBuffered(true);
        title = binder.buildAndBind("Title", POJO_TITLE, TextField.class);
        publisher = binder.buildAndBind("Publisher", POJO_PUBLISHER, TextField.class);
        yearOfIssue = binder.buildAndBind("Year of issue", POJO_YEAR_OF_ISSUE, TextField.class);
        city = binder.buildAndBind("City", POJO_CITY, TextField.class);

        genre = createGenreComponent();
        author = createAuthorComponent();

        fieldsLayout.addComponents(title, author, genre, publisher, yearOfIssue, city);
        title.focus();
    }

    @Override
    protected void save() {
        try {
            binder.commit();
            if (book.getIdBook() == 0) {
                booksService.addBook(book);
            } else {
                booksService.updateBook(book);
            }
            abstractView.refresh();
            close();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Please, check rejected fields");
        }
    }

    @Override
    protected void cancel() {
        binder.discard();
        close();
    }

    private NativeSelect createGenreComponent() {
        genre = new NativeSelect("Genre");
        genre.setContainerDataSource(new BeanItemContainer<>(Genre.class, genresService.getAllGenres()));
        genre.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        genre.setItemCaptionPropertyId(POJO_GENRE);
        binder.bind(genre, POJO_GENRE);
        return genre;
    }

    private NativeSelect createAuthorComponent() {
        author = new NativeSelect("Author");
        author.setContainerDataSource(new BeanItemContainer<>(
                Author.class, authorsService.getAllAuthors()));
        author.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
        author.setItemCaptionPropertyId(POJO_SURNAME);
        binder.bind(author, POJO_AUTHOR);
        return author;
    }

}
