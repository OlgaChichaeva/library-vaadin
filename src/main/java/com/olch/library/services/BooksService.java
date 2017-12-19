package com.olch.library.services;

import com.olch.library.persist.dao.hsqldb.BookDao;
import com.olch.library.persist.dao.hsqldb.filters.BooksFilter;
import com.olch.library.persist.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ольга on 16.12.2017.
 */
@Component
public class BooksService {

    @Autowired
    private BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.getAllBook();
    }

    public void addBook(Book book) {
        bookDao.insertBook(book);
    }

    public void updateBook(Book book) {
        bookDao.updateBook(book);
    }

    public void deleteBook(long id) {
        bookDao.deleteBook(id);
    }

    public int getBooksCountForGenre(long genreId) {
        return bookDao.getBooksCountForGenre(genreId);
    }

    public List<Book> getAllBooksByFilter(BooksFilter booksFilter) {
        return bookDao.getAllBooksByFilter(booksFilter);
    }

}
