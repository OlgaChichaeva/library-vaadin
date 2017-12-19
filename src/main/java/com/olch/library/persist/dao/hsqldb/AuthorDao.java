package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.entities.Author;

import java.util.List;

/**
 * Created by Ольга on 14.12.2017.
 */
public interface AuthorDao {

    List<Author> getAllAuthors();
    void insertAuthor(Author author);
    void updateAuthor(Author author);
    void deleteAuthor(long id);

}
