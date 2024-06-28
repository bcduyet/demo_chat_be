package co.jp.xeex.chat.domains.chatmngr.msg.service;

import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.RepplyMessageDetailDto;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.exception.BusinessException;

import java.util.List;

/**
 * ChatMessageService
 * 
 * @author q_thinh
 */
public interface ChatMessageService {

    public ChatMessageDto getChatMessageDtoById(String messageId);

    public ChatMessageDto getChatMessageDtoByDetailObj(ChatMessageDetailDto chatMessageDetailDto);

    public RepplyMessageDetailDto getRepplyMessageDetail(String repplyMessageId);

    public void saveChatFile(List<FileDto> files, ChatMessage chatMessage) throws BusinessException;

    public List<FileDto> getChatFileDto(String messageId);

    public void deleteOrEditChatMessage(ChatMessage chatMessage, String lang);
}
