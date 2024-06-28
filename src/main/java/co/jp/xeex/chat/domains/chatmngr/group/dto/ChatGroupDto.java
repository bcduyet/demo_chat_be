package co.jp.xeex.chat.domains.chatmngr.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChatGroupDto
 * 
 * @author q_thinh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupDto {
    private String groupId;
    private String groupName;
}
