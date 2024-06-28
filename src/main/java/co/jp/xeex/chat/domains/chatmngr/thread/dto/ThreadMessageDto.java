package co.jp.xeex.chat.domains.chatmngr.thread.dto;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import lombok.Data;

/**
 * ThreadMessageDto
 * 
 * @author q_thinh
 */
@Data
public class ThreadMessageDto {
    private String groupId;
    private String groupName;
    private String repplyMessageId;
    private ChatMessageDto message;
}
