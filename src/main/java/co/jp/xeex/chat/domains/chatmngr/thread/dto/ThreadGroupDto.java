package co.jp.xeex.chat.domains.chatmngr.thread.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ThreadGroupDto
 * 
 * @author q_thinh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadGroupDto {
    private String groupId;
    private String groupName;
    private String repplyMessageId;
    private Timestamp lastChatTime;
}
