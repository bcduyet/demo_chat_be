package co.jp.xeex.chat.domains.chat;

import java.util.List;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.RepplyMessageDetailDto;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Pattern;

/**
 * This class is a model for chat message.
 * <br>
 * It is used to send and receive chat messages between client/server.
 * 
 * @author v_long, q_thinh
 */
@SuppressWarnings({ "squid:S1104" })
public class ChatMessageDto extends RequestBase {
    /**
     * id of the group that the message is sent to<br>
     * set null from client side (it will be set by the server side)
     */
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String groupId;

    /**
     * id of the message gererated by the server<br>
     */
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String messageId;

    /**
     * time chat message
     */
    public String chatTime;

    /**
     * sender image
     */
    public String senderImage;

    /**
     * sender full Name
     */
    public String fullName;

    /**
     * the message content (text)
     * <br>
     * it can be a text/html message with format for displaying images, links, etc.
     */
    public String chatContent;

    /**
     * the id of the message that this message is a repply to
     */
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String repplyMessageId;

    /**
     * repply message detail info
     */
    public RepplyMessageDetailDto repplyMessage;

    /**
     * List chat files
     */
    public List<FileDto> chatFiles;

    /**
     * the list of users that are mentioned in the message.
     * <br>
     * used to notify the mentioned users (each user will receive a notification)
     * <br>
     * each user will mention in the message by using the "@" symbol followed by the
     * user name
     */
    public List<String> mentionedUserNames;

    /**
     * the id of the task info that the message is related to
     */
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String taskId;

    /**
     * the message action type
     * (LOGIN, LOGOUT, CHAT, JOIN, LEAVE, TYPING, REPPLY, REACTION,...)
     */
    public ChatAction action;
}
