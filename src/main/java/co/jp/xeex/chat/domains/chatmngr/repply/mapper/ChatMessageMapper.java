package co.jp.xeex.chat.domains.chatmngr.repply.mapper;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.entity.ChatMessage;

public interface ChatMessageMapper {
    /**
     * Mapping properties ChatMessage to ChatMessageDto
     * 
     * @param chatMessage
     * @return
     */
    public ChatMessageDto chatMessageToDto(ChatMessage chatMessage);

    /**
     * Mapping properties ChatMessageDetailDto to ChatMessageDto
     * 
     * @param chatMessage
     * @return
     */
    public ChatMessageDto chatMessageDetailToDto(ChatMessageDetailDto chatMessage);
}
