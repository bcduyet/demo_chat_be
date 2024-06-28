package co.jp.xeex.chat.domains.chat;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * The ChatMessageBroadcasrService interface provides methods to broadcast chat messages.
 * @author v_long
 */
public interface ChatMessageBroadcastService {

    /**
     * Broadcasts a chat message to a public group.
     *
     * @param msg The chat message to be broadcasted.
     * @return The broadcasted chat message.
     * @throws Exception If an error occurs during the broadcast.
     * @throws BusinessException If a business exception occurs during the broadcast.
     */
    ChatMessageDto broadcastMessageToPublicGroup(ChatMessageDto msg) throws BusinessException;

    /**
     * Broadcasts a chat message to a specific user.
     *
     * @param msgDto The chat message to be broadcasted.
     * @param receiverUser The username of the user to receive the message.
     * @return The broadcasted chat message.
     * @throws Exception If an error occurs during the broadcast.
     * @throws BusinessException If a business exception occurs during the broadcast.
     */
    ChatMessageDto broadcastMessageToUser(ChatMessageDto msgDto, String receiverUser) throws BusinessException;

    /**
     * Broadcasts a chat message to a specific group (groupId from msgDto).
     *
     * @param msgDto The chat message to be broadcasted.
     * @return The broadcasted chat message.
     * @throws Exception If an error occurs during the broadcast.
     * @throws BusinessException If a business exception occurs during the broadcast.
     */
    ChatMessageDto broadcastMessageToGroup(ChatMessageDto msgDto) throws BusinessException;
}
