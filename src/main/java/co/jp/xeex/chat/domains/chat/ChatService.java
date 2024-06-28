package co.jp.xeex.chat.domains.chat;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * The ChatService provides methods for sending chat messages to different targets.
 * @author v_long
 */
public interface ChatService {

    /**
     * Process ChatMessageDto and Sends a chat message to the public group.
     *
     * @param msg the chat message to send
     * @return the sent chat message
     * @throws Exception if an error occurs during the message sending process
     * @throws BusinessException if a business exception occurs during the message sending process
     */
    ChatMessageDto sendMessageToPublicGroup(ChatMessageDto msg) throws BusinessException;

    /**
     * Process ChatMessageDto and Sends a chat message to a specific user.
     *
     * @param msgDto the chat message to send
     * @return the sent chat message
     * @throws Exception if an error occurs during the message sending process
     * @throws BusinessException if a business exception occurs during the message sending process
     */
    ChatMessageDto sendMessageToUser(ChatMessageDto msgDto) throws BusinessException;

    /**
     * Process ChatMessageDto and Sends a chat message to a specific group.
     *
     * @param msgDto the chat message to send
     * @return the sent chat message
     * @throws Exception if an error occurs during the message sending process
     * @throws BusinessException if a business exception occurs during the message sending process
     */
    ChatMessageDto sendMessageToGroup(ChatMessageDto msgDto) throws BusinessException;

    /**
     * Sends a notification message to all users when a user logs in.
     * @param userName the user login
     * @param lang the current language
     * @return the notification message
     */
    void notifyLogin(String userName, String lang) throws BusinessException;

    /**
     * Sends a notification message to all users when a user logs out.
     * @param userName the user logout
     * @param lang the current language
     * @return the notification message
     */
    void notifyLogout(String userName, String lang) throws BusinessException;

    /**
     * Sends an error message to client.
     * @param receiver the receiver of the error message
     * @param e the business exception
     * @param lang the current language
     * @return the error message
     */
    void notifyError(String receiver,BusinessException e) throws BusinessException;

}
