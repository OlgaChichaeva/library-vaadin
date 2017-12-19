package com.olch.library.services;

import com.olch.library.persist.dao.hsqldb.AuthorDao;
import com.olch.library.persist.entities.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ольга on 16.12.2017.
 */
@Component
public class AuthorsService {

    @Autowired
    private AuthorDao authorDao;

    public List<Author> getAllAuthors() {
        return authorDao.getAllAuthors();
    }

    public void addAuthor(Author author) {
        authorDao.insertAuthor(author);
    }

    public void updateAuthor(Author author) {
        authorDao.updateAuthor(author);
    }

    public void deleteAuthor(long id) {
        authorDao.deleteAuthor(id);
    }

}
