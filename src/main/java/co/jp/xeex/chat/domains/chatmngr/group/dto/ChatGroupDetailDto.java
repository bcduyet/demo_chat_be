package co.jp.xeex.chat.domains.chatmngr.group.dto;

import java.util.List;

import lombok.Data;

/**
 * ChatGroupInfoDto
 * 
 * @author q_thinh
 */
@Data
public class ChatGroupDetailDto {
    private String groupId;
    private String groupName;
    /**
     * List member in group chat
     */
    private List<MemberDto> members;
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
