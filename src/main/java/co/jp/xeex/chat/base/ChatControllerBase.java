package co.jp.xeex.chat.base;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import com.google.gson.Gson;

import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.lang.resource.ResourceMessageItem;
import co.jp.xeex.chat.token.TokenClaimData;
import co.jp.xeex.chat.token.TokenType;
import co.jp.xeex.chat.validation.DtoValidateService;
import lombok.extern.log4j.Log4j;

/**
 * This is the base class for ChatController.<br>
 * 
 * @author v_long
 */
@Log4j
public class ChatControllerBase {
    private static final String CONTROLLER_BASE_INPUT_INVALID = "CONTROLLER_BASE_INPUT_INVALID";

    private static final String CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN = "CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN";

    @SuppressWarnings({ "squid:S6813" })
    @Autowired
    private DtoValidateService validation;
    /**
     * hold the current user name
     */
    protected String currentUserName = null;

    /**
     * Pre-process the ChatMessage.<br>
     * Include:<br>
     * - fill some fields if they are empty<br>
     * - validate the ChatMessage<br>
     * 
     * @param claimData
     * @param msgDto
     * @throws BusinessException
     */
    protected void preProcessChatMessage(ChatMessageDto msgDto, SimpMessageHeaderAccessor headerAccessor)
            throws BusinessException {
        if (headerAccessor != null && headerAccessor.getSessionAttributes() != null) {
            // claimData set in SocketHandshakeInterceptor
            @SuppressWarnings("null")
            TokenClaimData claimData = (TokenClaimData) headerAccessor.getSessionAttributes().get("token");
            // ưu tiên lấy info từ claimData
            if (claimData != null) {
                msgDto.lang = claimData.getLang();
                msgDto.requestBy = claimData.getUserName();
                // donot allow use Refresh token here
                if (claimData.getTokenType() == TokenType.REFRESH) {
                    throw new BusinessException(CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN, claimData.getLang());
                }
            }
        }
        if (null == msgDto) {
            throw new BusinessException(CONTROLLER_BASE_INPUT_INVALID);
        }
        // validate the ChatMessage
        List<ResourceMessageItem> errors = this.validation.getErrors(msgDto, msgDto.lang);
        if (null != errors) {
            BusinessException e = new BusinessException(CONTROLLER_BASE_INPUT_INVALID, msgDto.lang);
            e.setErrorData(errors);
            throw e;
        }
        // hold the current user name
        this.currentUserName = msgDto.requestBy;

        String json = new Gson().toJson(msgDto);
        log.info("preProcessChatMessage: " + json);
        log.info("Validate request: " + json);
    }

    /**
     * Create an error message.<br>
     * with this message, the client can know that the message is not sent
     * successfully.<br>
     * (use this message will never err with messageService)<br>
     * 
     * @param msgDto
     * @param e
     * @return
     */
    public ChatMessageDto createErrorChatMessage(ChatMessageDto msgDto, Exception e) {
        ChatMessageDto err = msgDto;
        err.chatContent = e.getMessage();
        err.action = ChatAction.ERROR;
        err.groupId = msgDto.groupId == null ? StringUtils.EMPTY : msgDto.groupId;
        err.requestBy = msgDto.requestBy == null || msgDto.requestBy.isEmpty() ? "system" : this.currentUserName;
        err.lang = msgDto.lang == null || msgDto.lang.isEmpty() ? "en" : msgDto.lang;
        err.repplyMessageId = msgDto.messageId;
        err.messageId = msgDto.messageId;
        return err;
    }

    /**
     * Create an error message from BussinessException.<br>
     * 
     * @param msgDto
     * @param e
     * @return
     */
    protected ChatMessageDto createErrorChatMessage(ChatMessageDto msgDto, BusinessException e) {
        ChatMessageDto err = msgDto;
        err.action = ChatAction.ERROR;
        err.groupId = msgDto.groupId == null ? StringUtils.EMPTY : msgDto.groupId;
        err.requestBy = msgDto.requestBy == null || msgDto.requestBy.isEmpty() ? "system" : this.currentUserName;
        err.lang = msgDto.lang == null || msgDto.lang.isEmpty() ? "en" : msgDto.lang;
        err.repplyMessageId = msgDto.messageId;
        err.messageId = msgDto.messageId;
        err.chatContent = "ERROR: ";
        //
        Object errors = e.getErrorData();
        if (errors != null) {
            @SuppressWarnings("unchecked")
            List<ResourceMessageItem> errorList = (List<ResourceMessageItem>) errors;
            StringBuilder sb = new StringBuilder();
            for (ResourceMessageItem item : errorList) {
                sb.append(item.toString()).append("; ");
            }
            err.chatContent += sb.toString();
        } else {
            err.chatContent = e.getMessage();
        }
        return err;
    }
}
