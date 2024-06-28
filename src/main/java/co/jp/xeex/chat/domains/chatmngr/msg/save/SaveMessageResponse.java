package co.jp.xeex.chat.domains.chatmngr.msg.save;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import lombok.Data;

/**
 * SaveMessageResponse
 * 
 * @author q_thinh
 */
@Data
public class SaveMessageResponse {
    ChatMessageDto message;
}
