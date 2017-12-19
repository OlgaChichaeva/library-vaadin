package com.olch.library.services;

import com.olch.library.persist.dao.hsqldb.GenreDao;
import com.olch.library.persist.entities.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Ольга on 16.12.2017.
 */
@Component
public class GenresService {

    @Autowired
    private GenreDao genreDao;

    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }

    public void addGenre(Genre genre) {
        genreDao.insertGenre(genre);
    }

    public void updateGenre(Genre genre) {
        genreDao.updateGenre(genre);
    }

    public void deleteGenre(long id) {
        genreDao.deleteGenre(id);
    }

}
