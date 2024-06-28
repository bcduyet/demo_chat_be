package co.jp.xeex.chat.lang.resource;

import co.jp.xeex.chat.base.ServiceBase;

/**
 * The service to get the message from the message resource.
 * (support the multi-language message)
 * @author v_long 
 */
public interface ResourceMessageService extends ServiceBase<ResourceMessageRequestDto, ResourceMessageResponseDto>{
    /**
     * Get the message by the message id and the language from the message resource.
     * @param msgId
     * @param lang
     * @return the message string
     */
    String getMessage(String msgId, String lang);  
}
