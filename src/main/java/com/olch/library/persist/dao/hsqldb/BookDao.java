package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.dao.hsqldb.filters.BooksFilter;
import com.olch.library.persist.entities.Book;

import java.util.List;

/**
 * Created by olch0914 on 15.12.2017.
 */
public interface BookDao {

    List<Book> getAllBook();
    void insertBook(Book book);
    void updateBook(Book book);
    void deleteBook(long id);
    List<Book> getAllBooksByFilter(BooksFilter booksFilter);
    int getBooksCountForGenre(long id);

}
