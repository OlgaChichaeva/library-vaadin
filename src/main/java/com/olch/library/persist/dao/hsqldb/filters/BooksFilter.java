package com.olch.library.persist.dao.hsqldb.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Ольга on 17.12.2017.
 */
@AllArgsConstructor
@Getter
@Setter
public class BooksFilter {
    private String authorSurname;
    private String publisher;
    private String title;
}
