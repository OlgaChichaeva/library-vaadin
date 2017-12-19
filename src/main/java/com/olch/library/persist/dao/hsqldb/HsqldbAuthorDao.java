package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.connections.hsqldb.HsqldbConnections;
import com.olch.library.persist.entities.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ольга on 14.12.2017.
 */
@Slf4j
@Component
public class HsqldbAuthorDao implements AuthorDao {

    private static final String SELECT_FROM_AUTHOR = "select * from author";
    private static final String INSERT_INTO_AUTHOR = "insert into author (surname, f_name,l_name) VALUES (?,?,?)";
    private static final String UPDATE_AUTHOR = "update author set surname = ?,f_name = ?,l_name = ? where id_author = ? ";
    private static final String DELETE_FROM_AUTHOR = "delete from author where id_author = ?";

    @Autowired
    private HsqldbConnections hsqldbConnection;

    @Override
    public List<Author> getAllAuthors() {
        try (Connection con = hsqldbConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_FROM_AUTHOR)) {
            List<Author> authors = new ArrayList<>();
            while (rs.next()) {
                Author author = convertToAuthor(rs);
                authors.add(author);
            }

            return authors;
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }


    @Override
    public void insertAuthor(Author author) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_INTO_AUTHOR)) {
            ps.setString(1, author.getSurname());
            ps.setString(2, author.getFName());
            ps.setString(3, author.getLName());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("addAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateAuthor(Author author) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_AUTHOR)) {
            ps.setString(1, author.getSurname());
            ps.setString(2, author.getFName());
            ps.setString(3, author.getLName());
            ps.setLong(4, author.getIdAuthor());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("updateAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteAuthor(long id) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_FROM_AUTHOR)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("deleteAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    private Author convertToAuthor(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setIdAuthor(rs.getLong("id_author"));
        author.setSurname(rs.getString("surname"));
        author.setFName(rs.getString("f_name"));
        author.setLName(rs.getString("l_name"));
        return author;
    }

}
