package com.olch.library.presentation.windows;

import com.olch.library.persist.entities.Author;
import com.olch.library.presentation.views.AbstractView;
import com.olch.library.services.AuthorsService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

/**
 * Created by Ольга on 17.12.2017.
 */
public class AuthorDialogWindow extends AbstractDialogWindow<Author>{

    private static final String POGO_SURNAME = "surname";
    private static final String POJO_F_NAME = "fName";
    private static final String POJO_L_NAME = "lName";

    private final AuthorsService authorsService;
    private final AbstractView abstractView;
    private Author author;
    private TextField surname;
    private TextField fName;
    private TextField lName;
    private final BeanFieldGroup<Author> binder = new BeanFieldGroup<>(Author.class);

    public AuthorDialogWindow(AbstractView abstractView, AuthorsService authorsService) {
        super();
        this.authorsService = authorsService;
        this.abstractView = abstractView;
    }

    @Override
    public void setEntity(Author author) {
        this.author = author;

        binder.setItemDataSource(author);
        binder.setBuffered(true);
        surname = binder.buildAndBind("Surname", POGO_SURNAME, TextField.class);
        fName = binder.buildAndBind("First name", POJO_F_NAME, TextField.class);
        lName = binder.buildAndBind("Last name", POJO_L_NAME, TextField.class);
        fieldsLayout.addComponents(surname, fName, lName);
        surname.focus();
    }

    @Override
    protected void save() {
        try {
            binder.commit();
            if (author.getIdAuthor() == 0) {
                authorsService.addAuthor(author);

            } else {
                authorsService.updateAuthor(author);
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

}
