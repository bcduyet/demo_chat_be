package co.jp.xeex.chat.domains.chatmngr.group.save;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.entity.ChatGroupMember;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatGroupMemberRepository;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SaveGroupServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class SaveGroupServiceImpl extends ServiceBaseImpl<SaveGroupRequest, SaveGroupResponse>
        implements SaveGroupService {

    // Error keys
    private static final String SAVE_GROUP_ERR_USER_IS_NOT_EXISTED = "SAVE_GROUP_ERR_USER_IS_NOT_EXISTED";
    private static final String SAVE_GROUP_ERR_PERMISSION_DENIED = "SAVE_GROUP_ERR_PERMISSION_DENIED";

    // DI
    private final ChatGroupRepository chatGroupRepo;
    private final ChatGroupMemberRepository chatGroupMemberRepo;
    private final UserRepository userRepo;
    private final ChatMessageBroadcastService chatMessageSendService;

    public SaveGroupServiceImpl(ChatGroupRepository chatGroupRepo,
            ChatGroupMemberRepository chatGroupMemberRepo,
            UserRepository userRepo,
            ChatMessageBroadcastService chatMessageSendService) {
        this.chatGroupRepo = chatGroupRepo;
        this.chatGroupMemberRepo = chatGroupMemberRepo;
        this.userRepo = userRepo;
        this.chatMessageSendService = chatMessageSendService;
    }

    @Override
    @Transactional
    public SaveGroupResponse processRequest(SaveGroupRequest in) throws BusinessException {
        ChatGroup chatGroup = new ChatGroup();
        List<String> oldMembers = new ArrayList<>();
        List<ChatGroupMember> oldMemberObjs = new ArrayList<>();
        Optional<ChatGroup> chatGroupOpt = chatGroupRepo.findById(in.groupId);

        // Get old members by group
        if (chatGroupOpt.isPresent()) {
            // Set chatgroup update object
            chatGroup = chatGroupOpt.get();
            chatGroup.setUpdateBy(in.requestBy);

            // Filter old members
            oldMemberObjs = chatGroupMemberRepo.findMembersByGroup(in.groupId);
            oldMembers = oldMemberObjs.stream().map(ChatGroupMember::getMemberEmpCd)
                    .collect(Collectors.toList());

            // Check permission user
            if (!oldMembers.contains(in.requestBy)) {
                throw new BusinessException(SAVE_GROUP_ERR_PERMISSION_DENIED, in.lang);
            }
        } else {
            // Setting create group member(keyMember)
            chatGroup.initDefault(in.requestBy);
            chatGroup.setGroupType(true);
        }

        // Save group
        chatGroup.setGroupName(in.groupName);
        chatGroupRepo.saveAndFlush(chatGroup);

        // v_long: notify to all new members in group
        ChatMessageDto notifyMessage = new ChatMessageDto();
        notifyMessage.groupId = chatGroup.getId();
        notifyMessage.chatContent = chatGroup.getGroupName();
        notifyMessage.messageId = "-1";
        notifyMessage.requestBy = in.requestBy;
        notifyMessage.lang = in.lang;

        // Add requestBy member and setting new mentionedUserNames
        in.members.add(in.requestBy);
        Set<String> newMembers = new HashSet<>(in.members);
        notifyMessage.mentionedUserNames = new ArrayList<>(newMembers);

        // Save new member group
        for (String member : newMembers) {
            // validation member
            if (userRepo.findByUserName(member) == null) {
                throw new BusinessException(SAVE_GROUP_ERR_USER_IS_NOT_EXISTED, new String[] { member }, in.lang);
            }

            // Add new member
            if (!oldMembers.contains(member)) {
                ChatGroupMember chatGroupMember = new ChatGroupMember();
                chatGroupMember.setGroupId(chatGroup.getId());
                chatGroupMember.setMemberEmpCd(member);
                chatGroupMemberRepo.saveAndFlush(chatGroupMember);

                // v_long: send notification add group name to new member
                if (!in.requestBy.equals(member)) {
                    notifyMessage.action = ChatAction.ADD_NEW_CHAT_GROUP;
                    chatMessageSendService.broadcastMessageToUser(notifyMessage, member);
                }
            }
        }

        // Delete member in group
        deleteMember(oldMemberObjs, newMembers, notifyMessage);

        // create new token and set to response
        SaveGroupResponse response = new SaveGroupResponse();
        response.setGroupId(chatGroup.getId());
        response.setGroupName(chatGroup.getGroupName());
        response.setKeyMember(chatGroup.getCreateBy());
        response.setMembers(new ArrayList<>(newMembers));
        return response;
    }

    /**
     * deleteMember
     * 
     * @param oldMemberObjs
     * @param newMembers
     * @param notifyMessage
     * @throws Exception
     */
    private void deleteMember(List<ChatGroupMember> oldMemberObjs, Set<String> newMembers, ChatMessageDto notifyMessage)
            throws BusinessException {
        // Delete member in group
        for (ChatGroupMember oldMemberObj : oldMemberObjs) {
            if (!newMembers.contains(oldMemberObj.getMemberEmpCd())) {
                chatGroupMemberRepo.delete(oldMemberObj);

                // v_long: send notification when delete user in group
                notifyMessage.action = ChatAction.DELETE_CHAT_GROUP;
                chatMessageSendService.broadcastMessageToUser(notifyMessage, oldMemberObj.getMemberEmpCd());
            } else {
                // v_long: send notification edit group name to old member
                notifyMessage.action = ChatAction.EDIT_CHAT_GROUP;
                chatMessageSendService.broadcastMessageToUser(notifyMessage, oldMemberObj.getMemberEmpCd());
            }
        }
    }
}
