package co.jp.xeex.chat.domains.chat;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.UUID;
import org.springframework.stereotype.Service;

import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.lang.resource.ResourceMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

/**
 * implementation of SystemMessageDtoFactotyService.<br>
 * 
 * @author v_long
 */
@Log4j
@AllArgsConstructor
@Service
public class SystemMessageDtoFactotyServiceImpl implements SystemMessageDtoFactoryService {
    private static final String INFO_MSG_USER_JOIN_CHAT = "INFO_MSG_USER_JOIN_CHAT";
    private static final String INFO_MSG_USER_LEAVE_CHAT = "INFO_MSG_USER_LEAVE_CHAT";
    private ResourceMessageService multiLangMessageService;

    @Override
    public ChatMessageDto createLogoutMessage(String currentUserName, String lang) {
        ChatMessageDto msgDto = new ChatMessageDto();
        msgDto.requestBy = currentUserName;
        msgDto.groupId =  UUID.randomUUID().toString();
        msgDto.messageId  = UUID.randomUUID().toString();
        msgDto.action = ChatAction.LEAVE;
        msgDto.chatContent = String.format(multiLangMessageService.getMessage(INFO_MSG_USER_LEAVE_CHAT, lang),
                currentUserName);
        setChatTime(msgDto);
        return msgDto;
    }

    @Override
    public ChatMessageDto createLoginMessage(String currentUserName, String lang) {
        ChatMessageDto msgDto = new ChatMessageDto();
        msgDto.requestBy = currentUserName;
        msgDto.groupId = UUID.randomUUID().toString();
        msgDto.messageId = UUID.randomUUID().toString();
        msgDto.action = ChatAction.JOIN;
        msgDto.chatContent = String.format(multiLangMessageService.getMessage(INFO_MSG_USER_JOIN_CHAT, lang), currentUserName);
        setChatTime(msgDto);
        return msgDto;
    }

    @Override
    public ChatMessageDto createErrorMessage(String groupId, String currentUserName, BusinessException e, String lang) {
        String message = String.format(multiLangMessageService.getMessage(e.getMessageId(), lang),
                (Object[]) e.getMsgParams());
        //
        log.error(message);
        //
        ChatMessageDto msgDto = new ChatMessageDto();
        msgDto.requestBy = currentUserName;
        msgDto.groupId = groupId;
        msgDto.messageId = "-1";
        msgDto.action = ChatAction.ERROR;
        msgDto.chatContent = message;
        setChatTime(msgDto);
        return msgDto;
    }

    @Override
    public void setChatTime(ChatMessageDto msgDto) {
        msgDto.chatTime = ZonedDateTime.now().format(DateTimeFormatter.ofPattern(AppConstant.DATE_TIME_ZONE_FORMAT));
    }

}
