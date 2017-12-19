package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.connections.hsqldb.HsqldbConnections;
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
public class HsqldbGenreDao implements GenreDao {

    private static final String SELECT_FROM_GENRE = "select * from genre";
    private static final String INSERT_INTO_GENRE = "insert into genre (genre) VALUES (?)";
    private static final String UPDATE_GENRE = "update genre set genre = ? where id_genre = ?";
    private static final String DELETE_FROM_GENRE = "delete from genre where id_genre = ?";

    @Autowired
    private HsqldbConnections hsqldbConnection;

    @Override
    public List<Genre> getAllGenres() {
        try (Connection con = hsqldbConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_FROM_GENRE)) {
            List<Genre> genres = new ArrayList<>();
            while (rs.next()) {
                convertToGenre(genres, rs);
            }

            return genres;
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void insertGenre(Genre genre) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_INTO_GENRE)) {
            ps.setString(1, genre.getGenre());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateGenre(Genre genre) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_GENRE)) {
            ps.setString(1, genre.getGenre());
            ps.setLong(2, genre.getIdGenre());
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteGenre(long id) {
        try (Connection con = hsqldbConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_FROM_GENRE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            log.error("getAllAuthor", ex);
            throw new RuntimeException(ex);
        }
    }

    private void convertToGenre(List<Genre> genres, ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setIdGenre(rs.getLong("id_genre"));
        genre.setGenre(rs.getString("genre"));
        genres.add(genre);
    }

}
