package com.olch.library.validation.constraints;

import com.olch.library.validation.validators.BookGenreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Ольга on 18.12.2017.
 */
@Documented
@Constraint(validatedBy = BookGenreValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookGenreConstraint {
    String message() default "Invalid book genre";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}