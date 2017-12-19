package com.olch.library.persist.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by olch0914 on 15.12.2017.
 */
@Getter
@Setter
public class Genre {

    private long idGenre;

    @Override
    public String toString() {
        return genre;
    }

    @NotEmpty
    @Size(min = 2, max = 50)
    private String genre;

}
