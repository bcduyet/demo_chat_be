package co.jp.xeex.chat.domains.chat.unread;

import org.springframework.stereotype.Service;

import co.jp.xeex.chat.entity.ChatGroupMember;
import co.jp.xeex.chat.entity.UnreadMessage;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatGroupMemberRepository;
import co.jp.xeex.chat.repository.UnreadMessageRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import lombok.AllArgsConstructor;

/**
 * implements for chat unread service
 * 
 * @author v_long
 */
@Service
@AllArgsConstructor
public class ChatUnreadServiceImpl implements ChatUnreadService {
    private static final String UNREAD_MESSAGE_NOT_FOUND = "UNREAD_MESSAGE_NOT_FOUND";
    private UnreadMessageRepository unreadMessageRepository;
    private ChatGroupMemberRepository chatGroupMemberRepository;

    @Override
    public GetChatUnreadReseponse getUnread(GetChatUnreadRequest request) throws BusinessException {
        UnreadMessage unreadMessage = unreadMessageRepository.getUnreadMessage(request.userId,
                request.chatGroupId);
        if (unreadMessage == null) {
            throw new BusinessException(UNREAD_MESSAGE_NOT_FOUND, request.lang);
        }
        GetChatUnreadReseponse response = new GetChatUnreadReseponse();
        response.setUserId(unreadMessage.getUserId());
        response.setChatGroupId(unreadMessage.getChatGroupId());
        response.setUnreadCount(unreadMessage.getUnreadCount());
        return response;
    }

    @Override
    public SetChatUnreadResponse setUnread(SetChatUnreadRequest request) {
        UnreadMessage unreadMessage = unreadMessageRepository.getUnreadMessage(request.userId,
                request.chatGroupId);

        Integer unreadCount = request.unreadCount != null ? request.unreadCount : 0;

        // create a new unread message if not found (not yet created before)
        if (unreadMessage == null) {
            unreadMessage = new UnreadMessage();
            unreadMessage.initDefault(request.userId);
            unreadMessage.setChatGroupId(request.chatGroupId);
            unreadMessage.setUserId(request.userId);
            unreadMessage.setUnreadCount(unreadCount);
        } else {
            unreadMessage.setUnreadCount(unreadCount);
        }
        unreadMessageRepository.saveAndFlush(unreadMessage);
        //
        // create response
        SetChatUnreadResponse response = new SetChatUnreadResponse();
        response.setUserId(unreadMessage.getUserId());
        response.setChatGroupId(unreadMessage.getChatGroupId());
        response.setUnreadCount(unreadMessage.getUnreadCount());
        return response;
    }

    @Override
    @Transactional
    public void increamentUnreadCountForChatGroup(String chatGroupId) {
        // 1. list all members from group
        List<ChatGroupMember> members = chatGroupMemberRepository.findMembersByGroup(chatGroupId);

        // 2. increament unread count for each member
        for (ChatGroupMember m : members) {
            UnreadMessage unreadMessage = unreadMessageRepository.getUnreadMessage(m.getMemberEmpCd(), chatGroupId);
            // create new if not yet exists
            if (unreadMessage == null) {
                unreadMessage = new UnreadMessage();
                unreadMessage.initDefault("system");
                unreadMessage.setChatGroupId(chatGroupId);
                unreadMessage.setUserId(m.getMemberEmpCd());
            }
            unreadMessage.setUnreadCount(unreadMessage.getUnreadCount() + 1);
            unreadMessageRepository.save(unreadMessage);
        }

        // commit
        unreadMessageRepository.flush();
    }

    @Override
    public void increamentUnreadCountForChatFriend(String chatGroupId, String friendCd) {
        UnreadMessage unreadMessage = unreadMessageRepository.getUnreadMessage(friendCd, chatGroupId);
        // create a new unread message if not found (not yet created before)
        if (unreadMessage == null) {
            unreadMessage = new UnreadMessage();
            unreadMessage.initDefault("system");
            unreadMessage.setChatGroupId(chatGroupId);
            unreadMessage.setUserId(friendCd);
            unreadMessage.setUnreadCount(1);
        } else {
            unreadMessage.setUnreadCount(unreadMessage.getUnreadCount() + 1);
        }
        unreadMessageRepository.saveAndFlush(unreadMessage);
    }
}
