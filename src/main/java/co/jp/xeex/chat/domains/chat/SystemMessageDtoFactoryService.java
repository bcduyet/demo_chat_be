package co.jp.xeex.chat.domains.chat;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * This interface provides methods to create system message DTOs for chat
 * messages.
 */
public interface SystemMessageDtoFactoryService {

    /**
     * Creates a logout message DTO.
     *
     * @param groupId  the chat group ID to send the message
     * @param userName the user name
     * @param lang     the language
     * @return the created ChatMessageDto object
     */
    ChatMessageDto createLogoutMessage(String userName, String lang);

    /**
     * Creates a login message DTO.
     *
     * @param groupId  the chat group ID to send the message
     * @param userName the user name
     * @param lang     the language
     * @return the created ChatMessageDto object
     */
    ChatMessageDto createLoginMessage(String userName, String lang);

    /**
     * Creates an error message DTO.
     *
     * @param groupId   the chat group ID to send the message
     * @param createBy  the creator of the message
     * @param exception the business exception
     * @param lang      the language
     * @return the created ChatMessageDto object
     */
    ChatMessageDto createErrorMessage(String groupId, String createBy, BusinessException exception, String lang);

    /**
     * Sets the chat time for the given message DTO.
     *
     * @param msgDto the ChatMessageDto object
     */
    void setChatTime(ChatMessageDto msgDto);
}
