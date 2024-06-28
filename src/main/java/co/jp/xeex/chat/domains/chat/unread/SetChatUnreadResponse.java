package co.jp.xeex.chat.domains.chat.unread;

import lombok.Data;

@Data
public class SetChatUnreadResponse {
    private String userId;
    /**
     * the chat group id associated with the chat messages
     */
    private String chatGroupId;
    /**
     * count of unread messages (response)
     */
    private Integer unreadCount;
}
