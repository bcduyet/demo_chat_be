package co.jp.xeex.chat.domains.chatmngr.friend.add;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.entity.ChatFriend;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDto;
import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDetailDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.repository.ChatFriendRepository;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * AddFriendServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class AddFriendServiceImpl extends ServiceBaseImpl<AddFriendRequest, AddFriendResponse>
        implements AddFriendService {

    private static final String ADD_FRIEND_ERR_USER_IS_NOT_EXISTED = "ADD_FRIEND_ERR_USER_IS_NOT_EXISTED";

    // DI
    private final ChatFriendRepository chatFriendsRepo;
    private final UserRepository userRepo;
    private final ChatGroupRepository chatGroupRepo;
    private final ChatMessageBroadcastService chatMessageSendService;

    public AddFriendServiceImpl(ChatGroupRepository chatGroupRepo,
            ChatFriendRepository chatFriendsRepo,
            UserRepository userRepo,
            ChatMessageBroadcastService chatMessageSendService) {
        this.chatGroupRepo = chatGroupRepo;
        this.chatFriendsRepo = chatFriendsRepo;
        this.userRepo = userRepo;
        this.chatMessageSendService = chatMessageSendService;
    }

    @Override
    @Transactional
    public AddFriendResponse processRequest(AddFriendRequest in) throws BusinessException {
        // Get list old friend
        List<FriendDto> oldFriendObjs = chatFriendsRepo.findByValue(in.requestBy, StringUtils.EMPTY);
        List<String> oldFriends = new ArrayList<>();
        if (!oldFriendObjs.isEmpty()) {
            // Filter old friend
            oldFriends = oldFriendObjs.stream().map(FriendDto::getEmpCd)
                    .collect(Collectors.toList());
        }

        // Setting notify
        ChatMessageDto notifyMessage = new ChatMessageDto();
        notifyMessage.messageId = "-1";
        notifyMessage.requestBy = in.requestBy;
        notifyMessage.lang = in.lang;
        notifyMessage.action = ChatAction.ADD_FRIEND;

        List<FriendDetailDto> newFriendDetails = new ArrayList<>();
        for (String empCdFriend : in.empCdFriends) {
            // Validation member
            if (userRepo.findByUserName(empCdFriend) == null) {
                throw new BusinessException(ADD_FRIEND_ERR_USER_IS_NOT_EXISTED,
                        new String[] { empCdFriend },
                        in.lang);
            }

            // Add friend
            if (!in.requestBy.equals(empCdFriend) && !oldFriends.contains(empCdFriend)) {
                // Create chat group
                ChatGroup chatGroup = new ChatGroup();
                chatGroup.setGroupName(in.requestBy.concat(", " + empCdFriend));
                chatGroup.setCreateBy(in.requestBy);
                chatGroup.setGroupType(false);
                chatGroup.initDefault(empCdFriend);
                chatGroupRepo.saveAndFlush(chatGroup);

                ChatFriend friend = new ChatFriend();
                friend.setEmpCd1(in.requestBy);
                friend.setEmpCd2(empCdFriend);
                friend.setGroupId(chatGroup.getId());
                friend.initDefault(in.requestBy);
                chatFriendsRepo.saveAndFlush(friend);

                friend = new ChatFriend();
                friend.setEmpCd1(empCdFriend);
                friend.setEmpCd2(in.requestBy);
                friend.setGroupId(chatGroup.getId());
                friend.initDefault(empCdFriend);
                chatFriendsRepo.saveAndFlush(friend);

                // Setting friend response
                FriendDetailDto friendDetailDto = new FriendDetailDto();
                friendDetailDto.setGroupId(chatGroup.getId());
                friendDetailDto.setEmpCd(empCdFriend);
                friendDetailDto.setStartMessageId(null);
                newFriendDetails.add(friendDetailDto);

                // Send notify to user
                notifyMessage.groupId = chatGroup.getId();
                notifyMessage.chatContent = empCdFriend;
                chatMessageSendService.broadcastMessageToUser(notifyMessage, empCdFriend);
            }
        }

        // Response
        AddFriendResponse response = new AddFriendResponse();
        response.setFriends(newFriendDetails);
        return response;
    }
}
