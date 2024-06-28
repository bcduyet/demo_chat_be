package co.jp.xeex.chat.validation;

/**
 * This class is used to load validation messages from a properties file.
 * (use a resource to make validation notation in dto classes)<br>
 * Remarks:<br>
 * - In separate domains, this class can be inherited to define separate
 * ValidateResources.<br>
 * 
 * @author v_long
 */
public class DtoValidateConsts {
    //
    // CONSTANTS DEFINITION MESSAGE KEYS AND PATTERNS TO CHECK INPUT DATA
    //
    /** [%s]: Incorrect date format {format} */
    public static final String VALIDATE_ERR_DATE_FORMAT = "VALIDATE_ERR_DATE_FORMAT";
    /** [%s]: Date must be after date {value} */
    public static final String VALIDATE_ERR_DATE_AFTER = "VALIDATE_ERR_DATE_AFTER";
    /** [%s]: Date must be before date {value} */
    public static final String VALIDATE_ERR_DATE_BEFORE = "VALIDATE_ERR_DATE_BEFORE";
    /** [%s]: Date must be {value} */
    public static final String VALIDATE_ERR_DATE_EQUA = "VALIDATE_ERR_DATE_EQUA";
    /** [%s]: Maximum length is {max} */
    public static final String VALIDATE_ERR_LEN_MAX = "VALIDATE_ERR_LEN_MAX";
    /** [%s]: Less than the minimum length of {min} */
    public static final String VALIDATE_ERR_LEN_MIN = "VALIDATE_ERR_LEN_MIN";
    /** [%s]: Violation of length {min}~{max} */
    public static final String VALIDATE_ERR_LEN_MIN_MAX = "VALIDATE_ERR_LEN_MIN_MAX";
    /** [%s]: Empty is not allowed */
    public static final String VALIDATE_ERR_EMPTY = "VALIDATE_ERR_EMPTY";
    /** [%s]: Exceeds the maximum value of {value} */
    public static final String VALIDATE_ERR_VAL_MAX = "VALIDATE_ERR_VAL_MAX";
    /** [%s]: Less than the minimum value of {value} */
    public static final String VALIDATE_ERR_VAL_MIN = "VALIDATE_ERR_VAL_MIN";
    /** [%s]: Incorrect {regexp} format */
    public static final String VALIDATE_ERR_PATTERN = "VALIDATE_ERR_PATTERN";
    /** [%s]: Incorrect decimal format {precision} */
    public static final String VALIDATE_ERR_DECIMAL = "VALIDATE_ERR_DECIMAL";
    /** [%s]: Email is incorrect */
    public static final String VALIDATE_ERR_EMAIL = "VALIDATE_ERR_EMAIL";
    /** [%s]: Must be haft-width, digits 0~9 */
    public static final String VALIDATE_ERR_HW_NUMBER = "VALIDATE_ERR_HW_NUMBER";
    /** [%s]: Array size must be {min} items and {max} items */
    public static final String VALDATE_ERR_ARRAY_SIZE = "VALDATE_ERR_ARRAY_SIZE";
    /**
     * [%s]: Must be half-width, no spaces and must have: numbers, uppercase
     * letters, lowercase letters and special characters
     */
    public static final String VALIDATE_ERR_HW_PASSWORD = "VALIDATE_ERR_HW_PASSWORD";
    /** [%s]: Must be haft-width, lower case a~z */
    public static final String VALIDATE_ERR_HW_LOWCASE = "VALIDATE_ERR_HW_LOWCASE";
    /** [%s]: Must be half-width, uppercase A~Z */
    public static final String VALIDATE_ERR_HW_UPCASE = "VALIDATE_ERR_HW_UPCASE";
    /** [%s]: Includes only the haft-width characters a~z, A~Z, 0~9 */
    public static final String VALIDATE_ERR_HW_ALL = "VALIDATE_ERR_HW_ALL";
    /** [%s]: Must not contain space characters (haft-width) */
    public static final String VALIDATE_ERR_HW_NO_SPACE = "VALIDATE_ERR_HW_NO_SPACE";
    /** [%s]: Id value is not uuid format (8-4-4-4-12 characters, 0-9, a-f, A-F) */
    public static final String VALIDATE_ERR_ID_UUID_INVALID = "VALIDATE_ERR_ID_UUID_INVALID";
    /** [%s]: Date value is not date format (4-2-2 number, 0-9) */
    public static final String VALIDATE_ERR_DATE_INVALID = "VALIDATE_ERR_DATE_INVALID";
    /** [%s]: Value must not be null */
    public static final String VALIDATE_ERR_NOT_NULL = "VALIDATE_ERR_NOT_NULL";

    //
    /** [%s]: 半角、数字 0 ～ 9 である必要があります。 */
    public static final String PATTERN_HW_NUMBER = "^[0-9]+$";
    /** [%s]: 半角でスペースは使用せず、数字、大文字、小文字、特殊文字を含める必要があります。 */
    public static final String PATTERN_HW_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(\\S+)$";
    /** [%s]: 半角小文字の a ～ z にする必要があります */
    public static final String PATTERN_HW_LOWCASE = "^[a-z]+$";
    /** [%s]: 半角、大文字のA～Zでなければなりません */
    public static final String PATTERN_HW_UPCASE = "^[A-Z]+$";
    /** [%s]: 半角文字a～z、A～Z、0～9のみを含みます。 */
    public static final String PATTERN_HW_ALL = "^[a-zA-Z0-9]+$";
    /** [%s]: スペース文字（半角）を含めることはできません */
    public static final String PATTERN_HW_NO_SPACE = "^[^\\s]*$";
    /** [%s]: UUID pattern */
    public static final String PATTERN_UUID_STRING = "^([a-fA-F0-9]{8}(-?[a-fA-F0-9]{4}){3}-?[a-fA-F0-9]{12}|)$";
    /** [%s]: Date pattern */
    public static final String PATTERN_DATE_STRING = "^(\\d{4}-\\d{2}-\\d{2}|)$";
}
