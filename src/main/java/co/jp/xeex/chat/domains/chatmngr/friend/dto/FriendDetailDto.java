package co.jp.xeex.chat.domains.chatmngr.friend.dto;

import lombok.Data;

/**
 * FriendDto
 * 
 * @author q_thinh
 */
@Data
public class FriendDetailDto {
    private String groupId;
    private String empCd;
    /**
     * message start in group chat
     * client use to check lazy load message history
     */
    private String startMessageId;
    /**
     * unread message count
     * client use to detect unread message in group
     */
    private Integer unreadMessageCount = 0;
}
