package co.jp.xeex.chat.domains.chatmngr.msg.get;

import java.util.List;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import lombok.Data;

/**
 * GetMessageResponse
 * 
 * @author q_thinh
 */
@Data
public class GetMessageResponse {
    private String groupId;
    private String groupName;
    List<ChatMessageDto> message;
}
