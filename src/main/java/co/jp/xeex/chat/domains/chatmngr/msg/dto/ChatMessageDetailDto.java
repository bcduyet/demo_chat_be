package co.jp.xeex.chat.domains.chatmngr.msg.dto;

import java.sql.Timestamp;

import co.jp.xeex.chat.domains.chat.ChatAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChatMessageInfoDto
 * 
 * @author q_thinh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDetailDto {
    private String groupId;
    private String messageId;
    private String repplyMessageId;
    private String sender;
    private String senderImage;
    private String fullName;
    private Timestamp chatTime;
    private String chatContent;
    private ChatAction action;
}
