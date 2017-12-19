package com.olch.library;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.olch.library.persist.dao.hsqldb.HsqldbAuthorDao;
import com.olch.library.presentation.views.AuthorsView;
import com.olch.library.presentation.views.BooksView;
import com.olch.library.presentation.views.GenresView;
import com.olch.library.services.AuthorsService;
import com.olch.library.services.BooksService;
import com.olch.library.services.GenresService;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Slf4j
@SpringUI
@Component
@Theme("mytheme")
public class MyUI extends UI {

    private static final String BOOKS_VIEW = "books";
    private static final String AUTHORS_VIEW = "authors";
    private static final String GENRE_VIEW = "genre";

    @Autowired
    private AuthorsService authorsService;

    @Autowired
    private BooksService booksService;

    @Autowired
    private GenresService genresService;

    @Autowired
    private HsqldbAuthorDao hsqldbAuthorDao;

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        log.info("Library Vaadin app has been started");

        getPage().setTitle("Library Vaadin");

        HorizontalLayout navigationMenu = createMenu();
        setContent(navigationMenu);
    }

    private HorizontalLayout createMenu() {
        Navigator navigator = new Navigator(this, this);
        HorizontalLayout navigationMenu = createMenuButtons(navigator);

        initNavigator(navigator, navigationMenu);

        navigationMenu.setSizeFull();
        navigationMenu.setMargin(true);

        return navigationMenu;
    }

    private HorizontalLayout createMenuButtons(Navigator navigator) {
        Button menuBook = new Button("Books",
          event -> navigator.navigateTo(BOOKS_VIEW));
        Button menuAuthor = new Button("Authors",
          event -> navigator.navigateTo(AUTHORS_VIEW));
        Button menuGenre = new Button("Genres",
          event -> navigator.navigateTo(GENRE_VIEW));
        return new HorizontalLayout(menuBook, menuAuthor, menuGenre);
    }

    private void initNavigator(Navigator navigator, HorizontalLayout navigationMenu) {
        // Create and register the views
        navigator.addView("", new BooksView(navigationMenu, booksService, genresService, authorsService));
        navigator.addView(BOOKS_VIEW, new BooksView(navigationMenu, booksService,genresService,authorsService));
        navigator.addView(AUTHORS_VIEW, new AuthorsView(navigationMenu, authorsService));
        navigator.addView(GENRE_VIEW, new GenresView(navigationMenu, genresService, booksService));
    }

}
