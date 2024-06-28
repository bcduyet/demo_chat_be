package co.jp.xeex.chat.lang.resource;

/**
 * The type of the message (SYSTEM, BUSINESS, VALIDATION,...)
 * @author v_long 
 */
public enum ResourceMessageTypes {
    /**
     * System message (info, warning, error from system)
     */
    SYSTEM, 
    /**
     * Business message (info, warning, error from business logic)
     */
    BUSINESS, 
    /**
     * Validation message (error message for input validation)
     */
    VALIDATION,
    /**
     * General message (info, warning, error from other sources)
     */
    GENERAL
}
