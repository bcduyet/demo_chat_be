package co.jp.xeex.chat.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * This annotation is used to apply date validation constraints on fields,
 * methods, parameters, or annotation types.
 * It is used in conjunction with the DateConstraintValidator class to perform
 * the validation.
 * 
 * @author v_long
 * 
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateConstraintValidator.class)
@Repeatable(DateConstraint.List.class)
public @interface DateConstraint {
    /**
     * The message to be displayed when the constraint is violated.
     */
    String message() default "";

    /**
     * The validation groups to which this constraint belongs.
     */
    Class<?>[] groups() default {};

    /**
     * The payload associated with this constraint.
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * The date format to be used. The default format is "yyyy-MM-dd".
     */
    String format() default "yyyy-MM-dd";

    /**
     * The type of comparison to be performed between the input value and the
     * comparison value.
     * The default is Compare.none (no comparison).
     */
    Compare compare() default Compare.NONE;

    /**
     * The comparison value to be used.
     * Give blank will equate to the current date
     */
    String value() default "";

    /**
     * The types of comparison available.
     */
    enum Compare {
        /** Compares input date equals valid value */
        EQUAL
        /** Compare input date must be LESS THAN (before) date of valid value */
        , BEFORE
        /** Compare input date must be GREATER THAN (after) valid value */
        ,AFTER, NONE
    }

    // support mutil anotation on each property
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
    @interface List {
        DateConstraint[] value();
    }
}