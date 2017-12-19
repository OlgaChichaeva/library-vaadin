package com.olch.library.presentation.windows;

import com.olch.library.persist.entities.Genre;
import com.olch.library.presentation.views.AbstractView;
import com.olch.library.services.GenresService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 * Created by Ольга on 17.12.2017.
 */
public class GenreDialogWindow extends AbstractDialogWindow<Genre> {

    private static final String POJO_GENRE = "genre";

    private final GenresService genresService;
    private final AbstractView abstractView;

    private Genre genre;
    private TextField genreName;
    final BeanFieldGroup<Genre> binder = new BeanFieldGroup<>(Genre.class);

    public GenreDialogWindow(GenresService genresService, AbstractView abstractView) {
        super();
        this.genresService = genresService;
        this.abstractView = abstractView;
    }

    @Override
    public void setEntity(Genre genre) {
        this.genre = genre;

        binder.setItemDataSource(genre);
        binder.setBuffered(true);
        genreName = binder.buildAndBind("Genre", POJO_GENRE, TextField.class);
        fieldsLayout.addComponents(genreName);
        genreName.focus();
    }

    @Override
    protected void save() {
        try {
            binder.commit();
            if (genre.getIdGenre() == 0) {
                genresService.addGenre(genre);
            } else {
                genresService.updateGenre(genre);
            }
            abstractView.refresh();
        } catch (FieldGroup.CommitException e) {
            Notification.show("Please, check rejected fields");
        }
    }

    @Override
    protected void cancel() {
        binder.discard();
        close();
    }

}
