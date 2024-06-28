package co.jp.xeex.chat.domains.chatmngr.msg.dto;

import java.util.List;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import lombok.Data;

/**
 * RepplyMessageDetailDto
 * 
 * @author q_thinh
 */
@Data
public class RepplyMessageDetailDto {
    /**
     * Total repply count
     */
    private Integer allRepply;
    /**
     * last repply chat time
     */
    private String lastRepply;
    /**
     * message start in group chat
     * client use to check lazy load message history
     */
    private String startMessageId;
    /**
     * All user repply
     */
    private List<SenderDto> allUserRepply;
    /**
     * Repply chat message history
     */
    private List<ChatMessageDto> message;
}
