package co.jp.xeex.chat.lang.resource;

import co.jp.xeex.chat.base.RequestBase;

/**
 * The request DTO to get the message from the message resource.
 * @author v_long
 */
public class ResourceMessageRequestDto extends RequestBase {    
    /**
     * Directive to retrieve all messages of all languages.<br>
     * (True: get all, False/null: get according to current language)
     */
    public boolean allLang = false;
}
