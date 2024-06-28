package co.jp.xeex.chat.domains.chatmngr.repply.mapper;

import org.springframework.stereotype.Service;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.util.DateTimeUtil;

@Service
public class ChatMessageMapperImpl implements ChatMessageMapper {
    @Override
    public ChatMessageDto chatMessageToDto(ChatMessage chatMessage) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.groupId = chatMessage.getGroupId();
        chatMessageDto.messageId = chatMessage.getId();
        chatMessageDto.requestBy = chatMessage.getCreateBy();
        chatMessageDto.chatTime = DateTimeUtil.getZoneDateTimeString(chatMessage.getCreateAt());
        chatMessageDto.repplyMessageId = chatMessage.getRepplyMessageId();
        chatMessageDto.chatContent = chatMessage.getChatContent();
        chatMessageDto.action = chatMessage.getAction();
        return chatMessageDto;
    }

    @Override
    public ChatMessageDto chatMessageDetailToDto(ChatMessageDetailDto chatMessageDetail) {
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.groupId = chatMessageDetail.getGroupId();
        chatMessageDto.messageId = chatMessageDetail.getMessageId();
        chatMessageDto.requestBy = chatMessageDetail.getSender();
        chatMessageDto.fullName = chatMessageDetail.getFullName();
        chatMessageDto.chatTime = DateTimeUtil.getZoneDateTimeString(chatMessageDetail.getChatTime());
        chatMessageDto.repplyMessageId = chatMessageDetail.getRepplyMessageId();
        chatMessageDto.chatContent = chatMessageDetail.getChatContent();
        chatMessageDto.action = chatMessageDetail.getAction();
        return chatMessageDto;
    }
}
