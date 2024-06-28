package co.jp.xeex.chat.domains.chat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import co.jp.xeex.chat.domains.chatmngr.msg.service.ChatMessageService;
import co.jp.xeex.chat.entity.ChatFriend;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFriendRepository;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@AllArgsConstructor
@Service
@Log4j
public class ChatServiceImpl implements ChatService {
    /** Fiend not fount */
    private static final String CHAT_MSG_PROCCESS_ERR_FRIEND_NOT_FOUND = "CHAT_MSG_PROCCESS_ERR_FRIEND_NOT_FOUND";
    private static final String CHAT_MSG_PROCCESS_ERR_GROUP_NOT_FOUND = "CHAT_MSG_PROCCESS_ERR_GROUP_NOT_FOUND";
    private ChatMessageRepository chatMessageRepository;
    private ChatFriendRepository chatFriendRepository;
    private ChatGroupRepository chatGroupRepository;
    private ChatMessageService chatMessageService;
    private SystemMessageDtoFactoryService systemMessageDtoFactotyService;
    private UserRepository userRepository;
    //
    // use to broadcast message to clients
    private ChatMessageBroadcastService broadcasrService;

    // PRIVATE METHODS

    private ChatMessage convertToMessage(ChatMessageDto msgDto) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setGroupId(msgDto.groupId);
        chatMessage.setId(msgDto.messageId);
        chatMessage.setId(msgDto.messageId);
        chatMessage.setChatContent(msgDto.chatContent == null ? StringUtils.EMPTY : msgDto.chatContent);
        chatMessage.setAction(msgDto.action);
        chatMessage.setCreateBy(msgDto.requestBy);
        chatMessage.setUpdateBy(msgDto.requestBy);
        chatMessage.setRepplyMessageId(msgDto.repplyMessageId);
        return chatMessage;
    }

    // IMPLEMENTED METHODS
    @Override
    public ChatMessageDto sendMessageToPublicGroup(ChatMessageDto msgDto) throws BusinessException {
        /**
         * process steps:
         * - Public group has id = 0
         * - convert msgDto to ChatMessage to save to database
         * - save message to database to get the message id before sending to all users
         * - send message to all users in the public group
         * - return msgDto with message id
         */
        msgDto.groupId = StringUtils.EMPTY;
        // broadcast message to all users
        broadcasrService.broadcastMessageToPublicGroup(msgDto);

        ChatMessage chatMessage = this.convertToMessage(msgDto);
        chatMessage = chatMessageRepository.saveAndFlush(chatMessage);
        msgDto.messageId = chatMessage.getId();
        return msgDto;
    }

    @Override
    public ChatMessageDto sendMessageToUser(ChatMessageDto msgDto) throws BusinessException {
        if (msgDto == null) {
            throw new BusinessException("SEND_MSG_ERR_MSG_EMPTY");
        }

        /**
         * process steps:
         * - check if the group chat exists (from CHAT_FRIEND table)
         * if not, throw exception: CHAT_FRIEND_NOT_FOUND
         * - get the receiver name
         * - convert msgDto to ChatMessage to save to database
         * - save message to database to get the message id before sending to the
         * receiver
         * - send message to the receiver (client subscribes to /user/{user
         * name}/private)
         * - return msgDto with message id
         */
        ChatFriend chatFriend = chatFriendRepository.findById(msgDto.groupId).orElse(null);
        if (chatFriend == null) {
            // report error to current user
            return this.feedBackError(msgDto,
                    new BusinessException(CHAT_MSG_PROCCESS_ERR_FRIEND_NOT_FOUND, msgDto.lang));
        }
        String friendName = chatFriend.getEmpCd1().equals(msgDto.requestBy) ? chatFriend.getEmpCd2()
                : chatFriend.getEmpCd1();

        // get friend name. May be null by many reasons
        if (friendName == null) {
            return this.feedBackError(msgDto,
                    new BusinessException(CHAT_MSG_PROCCESS_ERR_FRIEND_NOT_FOUND, msgDto.lang));
        }
        ChatMessage chatMessage = this.convertToMessage(msgDto);
        chatMessage = chatMessageRepository.saveAndFlush(chatMessage);

        // Save chat files
        chatMessageService.saveChatFile(msgDto.chatFiles, chatMessage);

        // Set broadcast data
        msgDto.messageId = chatMessage.getId();
        if (msgDto.repplyMessageId != null) {
            msgDto.repplyMessage = chatMessageService.getRepplyMessageDetail(msgDto.repplyMessageId);
        }

        // broadcast message to receiver
        return broadcasrService.broadcastMessageToUser(msgDto, friendName);
    }

    @Override
    public ChatMessageDto sendMessageToGroup(ChatMessageDto msgDto) throws BusinessException {
        /**
         * process steps:
         * - convert msgDto to ChatMessage to save to database
         * - save message to database to get the message id before sending to group
         * - send message to group. Client subscribes to /group/{groupiId}/private
         */
        // may need to check if groupID exists
        ChatGroup chatGroup = chatGroupRepository.findById(msgDto.groupId).orElse(null);
        if (chatGroup == null) {
            return this.feedBackError(msgDto,
                    new BusinessException(CHAT_MSG_PROCCESS_ERR_GROUP_NOT_FOUND, msgDto.lang));
        }
        // convert msgDto to ChatMessage to save to database
        ChatMessage chatMessage = this.convertToMessage(msgDto);
        chatMessage = chatMessageRepository.saveAndFlush(chatMessage);

        // Save chat files
        chatMessageService.saveChatFile(msgDto.chatFiles, chatMessage);

        // Set broadcast data
        msgDto.messageId = chatMessage.getId();
        if (msgDto.repplyMessageId != null) {
            msgDto.repplyMessage = chatMessageService.getRepplyMessageDetail(msgDto.repplyMessageId);
        }

        // Notify all other mention user
        if (!msgDto.mentionedUserNames.isEmpty()) {
            msgDto.action = ChatAction.MENTION;
            for (String mentionedUserName : msgDto.mentionedUserNames) {
                if (!msgDto.requestBy.equals(mentionedUserName)) {
                    broadcasrService.broadcastMessageToUser(msgDto, mentionedUserName);
                }
            }
        }

        // broadcast message to group
        msgDto.action = ChatAction.CHAT;
        return broadcasrService.broadcastMessageToGroup(msgDto);
    }

    private ChatMessageDto feedBackError(ChatMessageDto msgDto, BusinessException e) throws BusinessException {
        ChatMessageDto message = systemMessageDtoFactotyService.createErrorMessage(StringUtils.EMPTY, msgDto.requestBy,
                e, msgDto.lang);

        log.error(message);
        return broadcasrService.broadcastMessageToUser(message, msgDto.requestBy);
    }

    @Override
    public void notifyLogin(String currentUserName, String lang) throws BusinessException {
        List<User> users = userRepository.findUsersOnRelationshipOf(currentUserName);
        for (User user : users) {
            ChatMessageDto msg = systemMessageDtoFactotyService.createLoginMessage(currentUserName, lang);
            broadcasrService.broadcastMessageToUser(msg, user.getEmpCd());
        }
    }

    @Override
    public void notifyError(String userName, BusinessException e) throws BusinessException {
        ChatMessageDto msg = systemMessageDtoFactotyService.createErrorMessage("-1", userName, e,
                e.getLang() == null ? "en" : e.getLang());
        broadcasrService.broadcastMessageToUser(msg, userName);
    }

    @Override
    public void notifyLogout(String currentUserName, String lang) throws BusinessException {
        List<User> users = userRepository.findUsersOnRelationshipOf(currentUserName);
        for (User user : users) {
            ChatMessageDto msg = systemMessageDtoFactotyService.createLogoutMessage(currentUserName, lang);
            broadcasrService.broadcastMessageToUser(msg, user.getEmpCd());
        }
    }

}
