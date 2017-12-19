package com.olch.library.validation.validators;

import com.olch.library.AppConfig;
import com.olch.library.validation.constraints.BookGenreConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Ольга on 18.12.2017.
 */
public class BookGenreValidator implements ConstraintValidator<BookGenreConstraint, String> {

    private static final String GENRE_VALUE_ERROR = "The genre should be one of %s";

    @Override
    public void initialize(BookGenreConstraint bookGenreConstraint) {
    }

    @Override
    public boolean isValid(String publisher, ConstraintValidatorContext constraintContext) {
        if (publisher != null && AppConfig.publisherPossibleValues.contains(publisher)) {
            return true;
        }
        constraintContext.disableDefaultConstraintViolation();
        constraintContext.buildConstraintViolationWithTemplate(String.format(
                GENRE_VALUE_ERROR, AppConfig.publisherPossibleValues.toString()))
                .addConstraintViolation();
        return false;
    }

}
