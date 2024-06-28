package co.jp.xeex.chat.base;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The DTO as a body of a EntityResponse
 * @author v_long
 */
@Data
@NoArgsConstructor
public class ResponseBody {
    /**
     * The HTTP status code.
     */
    private int status;
    /**
     * The message ID associated with the HTTP response.
     */
    private String messageId;
    /**
     * The message associated with the HTTP response. 
     * (the MSG_ID from the message.properties file)
     */
    private String message;
    /**
     * The data associated with the HTTP response.
     */
    private Object data;
    /**
     * The error message associated with the HTTP response.
     */
    private boolean hasError;

    /**
     * The list of errors associated with the HTTP response.
     */
    private Object errors;
}
