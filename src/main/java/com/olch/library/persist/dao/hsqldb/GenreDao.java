package com.olch.library.persist.dao.hsqldb;

import com.olch.library.persist.entities.Genre;

import java.util.List;

/**
 * Created by olch0914 on 15.12.2017.
 */
public interface GenreDao {

    List<Genre> getAllGenres();
    void insertGenre(Genre genre);
    void updateGenre(Genre genre);
    void deleteGenre(long id);

}
