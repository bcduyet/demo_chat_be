package co.jp.xeex.chat.validation;

import co.jp.xeex.chat.validation.DateConstraint.Compare;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class is a custom validator for the DateConstraint annotation.
 * It validates if a given date string matches the specified format and
 * satisfies the comparison conditions.
 * 
 * @author v_long
 */
public class DateConstraintValidator implements ConstraintValidator<DateConstraint, String> {
    private String format;// giá trị format từ anotation
    private DateConstraint.Compare compare; // giá trị compare từ anotation
    private String attibuteValue; // giá trị value từ anotation

    /**
     * Initializes the validator with the specified constraint annotation.
     *
     * @param constraintAnnotation the DateConstraint annotation instance
     */
    @Override
    public void initialize(DateConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.format = constraintAnnotation.format();
        if (this.format == null || "".equals(this.format)) {
            this.format = "yyyy-MM-dd";
        }
        if (constraintAnnotation.value() == null || "".equals(constraintAnnotation.value())) {
            // set default value for attribute
            this.attibuteValue = LocalDate.now().format(DateTimeFormatter.ofPattern(this.format));
        } else {
            this.attibuteValue = constraintAnnotation.value();
        }

        this.compare = constraintAnnotation.compare();

    }

    /**
     * Validates if the given input value is a valid date string.
     *
     * @param inputValue the input value to be validated
     * @param context    the validation context
     * @return true if the input value is a valid date string, false otherwise
     */
    @Override
    public boolean isValid(String inputValue, ConstraintValidatorContext context) {
        if (this.format == null || "".equals(this.format)) {
            this.format = "yyyy-MM-dd";
        }
        // If the input value is empty, set it to the current date
        if (inputValue == null || "".equals(inputValue)) {
            inputValue = LocalDate.now().format(DateTimeFormatter.ofPattern(this.format));
        }

        // Check if the value matches the specified format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.format);
        try {
            // Check if the date format is ok?
            LocalDate inputDate = LocalDate.parse(inputValue, formatter);
            LocalDate attributeDate = LocalDate.parse(this.attibuteValue, formatter);
            if (this.compare == Compare.NONE || attributeDate == null) {
                // no need to compare anything
                return true;
            }
            // If ok, check the comparison conditions
            boolean ok = false;
            switch (this.compare) {
                case EQUAL:
                    ok = inputDate.equals(attributeDate);
                    break;
                case BEFORE:
                    ok = inputDate.isBefore(attributeDate);
                    break;
                case AFTER:
                    ok = inputDate.isAfter(attributeDate);
                    break;
                default:
                    ok = true;
                    break;
            }
            return ok;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}