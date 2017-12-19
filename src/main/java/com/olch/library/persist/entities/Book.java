package com.olch.library.persist.entities;

import com.olch.library.validation.constraints.BookGenreConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;


import javax.validation.constraints.*;

/**
 * Created by olch0914 on 15.12.2017.
 */

@Data
public class Book {

    private long idBook;

    @NotNull
    private Author author;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String title;

    @NotNull
    private Genre genre;

    @BookGenreConstraint
    @NotEmpty
    @Size(min = 2, max = 20)
    private String publisher;

    @NotEmpty
    @Size(min = 4, max = 4)
    private String yearOfIssue;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String city;

}
