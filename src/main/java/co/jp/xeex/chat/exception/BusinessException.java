package co.jp.xeex.chat.exception;

/**
 * The class to handle the business exceptions.<br>
 * (Use this exception to handle the business exceptions)
 * 
 * @author v_long, q_thinh
 */
public class BusinessException extends CommonExceptionImpl {

    private static final long serialVersionUID = 1L;

    public BusinessException(String key) {
        super(key);
    }

    /**
     * Constructor: create a new BussinessException object. with the key and lang
     * 
     * @param key  message key from message_*.properties
     * @param lang language code (en, jp, ...)
     */
    public BusinessException(String key, String lang) {
        super(key, lang);
    }

    /**
     * Constructor: create a new BussinessException object. with the key, msgParam,
     * and lang
     * 
     * @param key      message key from message_*.properties
     * @param msgParam message parameters
     * @param lang     language code (en, jp, ...)
     */
    public BusinessException(String key, String[] msgParam, String lang) {
        super(key, msgParam, lang);
    }

}
