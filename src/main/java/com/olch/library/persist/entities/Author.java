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
public class Author {

    private long idAuthor;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String surname;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String fName;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String lName;

    public String getFullName() {
        return surname + " " + fName + " " + lName;
    }

    @Override
    public String toString() {
        return getFullName();
    }

}
