package co.jp.xeex.chat.domains.chat.unread;

import lombok.Data;

/**
 * get count of unread message of groupChat of current user
 * @author v_long
 */
@Data
public class GetChatUnreadReseponse {
    private String userId;
    /**
     * the chat group id associated with the chat messages
     */
    private String chatGroupId;
    /**
     *  count of unread messages (response)
     */
    private Integer unreadCount;
}
