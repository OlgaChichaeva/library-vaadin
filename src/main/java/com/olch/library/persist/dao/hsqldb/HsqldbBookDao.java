package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.connections.hsqldb.HsqldbConnections;
import com.olch.library.persist.dao.hsqldb.filters.BooksFilter;
import com.olch.library.persist.entities.Author;
import com.olch.library.persist.entities.Book;
import com.olch.library.persist.entities.Genre;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olch0914 on 15.12.2017.
 */
@Slf4j
@Component
public class HsqldbBookDao implements BookDao {

    private static final String SELECT_FROM_BOOK = "select b.id_book, b.title, a.id_author, a.surname, a.f_name,a.l_name, g.id_genre, g.genre, b.publisher, b.year_of_issue, b.city from book b, genre g, author a where a.id_author=b.id_author and b.id_genre=g.id_genre";
    private static final String SELECT_FILTER = SELECT_FROM_BOOK +
            " and LOWER(b.title) LIKE LOWER(?) and LOWER(b.publisher) LIKE LOWER(?) AND" +
            " (LOWER(a.surname || a.f_name || a.l_name) LIKE LOWER(?))";
    private static final String INSERT_TO_BOOK = "insert into  book (id_author, title, id_genre, publisher, year_of_issue, city) values (?,?,?,?,?,?)";
    private static final String UPDATE_BOOK = "update book set id_author = ?, title = ?, id_genre = ?, publisher = ?, year_of_issue = ?, city = ? where id_book = ?";
    private static final String DELETE_BOOK = "delete from book where id_book = ?";
    private static final String COUNT_BOOK = "select COUNT(*) count_books FROM book WHERE id_genre = ?";

    @Autowired
    private HsqldbConnections hsqldbConnection;

    @Override
    public List<Book> getAllBook() {
        try (Connection con = hsqldbConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_FROM_BOOK)) {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = convertToBook(rs);
                books.add(book);
            }

            return books;
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void insertBook(Book book) {
        log.debug("insertBook book={}", book);
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_TO_BOOK)) {
            ps.setLong(1, book.getAuthor().getIdAuthor());
            ps.setString(2, book.getTitle());
            ps.setLong(3, book.getGenre().getIdGenre());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getYearOfIssue());
            ps.setString(6, book.getCity());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateBook(Book book) {
        log.debug("updateBook book: {}", book);
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_BOOK)) {
            ps.setLong(1, book.getAuthor().getIdAuthor());
            ps.setString(2, book.getTitle());
            ps.setLong(3, book.getGenre().getIdGenre());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getYearOfIssue());
            ps.setString(6, book.getCity());
            ps.setLong(7, book.getIdBook());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("updateBook", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteBook(long id) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_BOOK)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Book> getAllBooksByFilter(BooksFilter booksFilter) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(SELECT_FILTER)) {
            List<Book> books = new ArrayList<>();
            ps.setString(1, "%" + booksFilter.getTitle() + "%");
            ps.setString(2, "%" + booksFilter.getPublisher() + "%");
            ps.setString(3, "%" + booksFilter.getAuthorSurname() + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = convertToBook(rs);
                books.add(book);
            }
            return books;
        } catch (SQLException ex) {
            log.error("getByFilter", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public int getBooksCountForGenre(long id) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(COUNT_BOOK)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("count_books");
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    private Book convertToBook(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setIdAuthor(rs.getLong("id_author"));
        author.setSurname(rs.getString("surname"));
        author.setFName(rs.getString("f_name"));
        author.setLName(rs.getString("l_name"));
        Genre genre = new Genre();
        genre.setIdGenre(rs.getLong("id_genre"));
        genre.setGenre(rs.getString("genre"));

        Book book = new Book();
        book.setIdBook(rs.getLong("id_book"));
        book.setTitle(rs.getString("title"));
        book.setGenre(genre);
        book.setAuthor(author);
        book.setPublisher(rs.getString("publisher"));
        book.setYearOfIssue(rs.getString("year_of_issue"));
        book.setCity(rs.getString("city"));
        return book;
    }

}
