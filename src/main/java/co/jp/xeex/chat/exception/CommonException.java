package co.jp.xeex.chat.exception;

/**
 * The common exception interface.
 * (Use this interface to handle the common exceptions)<br>
 * supported language: en, jp, ...
 * 
 * @author v_long, q_thinh
 */
public interface CommonException {
    /**
     * Get the language.
     * 
     * @return
     */
    String getLang();

    /**
     * Get the message id.
     * 
     * @return
     */
    String getMessageId();

    /**
     * Get the message with supported language.
     * 
     * @return
     */
    String getMessage();

    /**
     * Get the error data.
     * 
     * @return
     */
    Object getErrorData();

    /**
     * Set the error data.
     * 
     * @param errorData
     */
    void setErrorData(Object errorData);

    /**
     * Get the message params.
     * 
     * @return
     */
    String[] getMsgParams();

    /**
     * Set the message params.
     * 
     * @param msgParams
     */
    void setMsgParams(String[] msgParams);   
}
