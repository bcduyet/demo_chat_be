package co.jp.xeex.chat.domains.chatmngr.group.delete;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.entity.ChatFile;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.entity.ChatGroupMember;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFileRepository;
import co.jp.xeex.chat.repository.ChatGroupMemberRepository;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.MessageTaskRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * DeleteGroupServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class DeleteGroupServiceImpl extends ServiceBaseImpl<DeleteGroupRequest, DeleteGroupResponse>
        implements DeleteGroupService {

    // Error keys
    private static final String DELETE_GROUP_ERR_GROUP_IS_NOT_EXISTED = "DELETE_GROUP_ERR_GROUP_IS_NOT_EXISTED";
    private static final String DELETE_GROUP_ERR_PERMISSION_DENIED = "DELETE_GROUP_ERR_PERMISSION_DENIED";

    // DI
    private ChatGroupRepository chatGroupRepo;
    private ChatGroupMemberRepository chatGroupMemberRepo;
    private ChatMessageRepository chatMessageRepo;
    private ChatFileRepository chatFileRepo;
    private FileRepository fileRepo;
    private MessageTaskRepository messageTaskRepo;
    private ChatMessageBroadcastService chatMessageSendService;
    private EnvironmentUtil environmentUtil;

    @Override
    @Transactional
    public DeleteGroupResponse processRequest(DeleteGroupRequest in) throws BusinessException {
        // Get chatGroup
        ChatGroup chatGroup = chatGroupRepo.findById(in.groupId).orElse(null);
        if (chatGroup == null) {
            throw new BusinessException(DELETE_GROUP_ERR_GROUP_IS_NOT_EXISTED, in.lang);
        }

        // Check permission user
        List<ChatGroupMember> chatGroupMembers = chatGroupMemberRepo.findMembersByGroup(in.groupId);
        List<String> nameChatGroupMembers = chatGroupMembers.stream().map(ChatGroupMember::getMemberEmpCd)
                .collect(Collectors.toList());
        if (!nameChatGroupMembers.contains(in.requestBy)) {
            throw new BusinessException(DELETE_GROUP_ERR_PERMISSION_DENIED, in.lang);
        }

        // Set notify content
        ChatMessageDto notifyMessage = new ChatMessageDto();
        notifyMessage.groupId = chatGroup.getId();
        notifyMessage.chatContent = chatGroup.getGroupName();
        notifyMessage.messageId = "-1";
        notifyMessage.requestBy = in.requestBy;
        notifyMessage.lang = in.lang;
        notifyMessage.action = ChatAction.EDIT_CHAT_GROUP;

        // Check delete or leave group
        if (chatGroupMembers.size() == 1) {
            // Delete chat files
            try {
                deleteChatFile(in.groupId);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), in.lang);
            }

            // Delete message task
            List<String> messageTaskIds = messageTaskRepo.getListIdByGroupId(in.groupId);
            messageTaskRepo.deleteAllById(messageTaskIds);

            // Delete chat message
            List<String> chatMessageIds = chatMessageRepo.getListIdByGroup(in.groupId);
            chatMessageRepo.deleteAllById(chatMessageIds);

            // Delete chat group
            chatGroupRepo.delete(chatGroup);

            // Set action notify
            notifyMessage.action = ChatAction.DELETE_CHAT_GROUP;
        }

        // Delete member and notify
        for (ChatGroupMember chatGroupMember : chatGroupMembers) {
            // Delete member
            if (in.requestBy.equals(chatGroupMember.getMemberEmpCd())) {
                chatGroupMemberRepo.delete(chatGroupMember);
            }

            // Send notify to user
            if (!in.requestBy.equals(chatGroupMember.getMemberEmpCd())) {
                chatMessageSendService.broadcastMessageToUser(notifyMessage, chatGroupMember.getMemberEmpCd());
            }
        }

        // Response
        DeleteGroupResponse response = new DeleteGroupResponse();
        response.setResult(true);
        return response;
    }

    /**
     * Delete chat files in group
     * 
     * @param groupId
     * @throws IOException
     */
    private void deleteChatFile(String groupId) throws IOException {
        // Delete chat files
        List<ChatFile> chatFiles = chatFileRepo.findByGroupId(groupId);
        List<String> chatFileIds = chatFiles.stream().map(ChatFile::getId).collect(Collectors.toList());
        chatFileRepo.deleteAllById(chatFileIds);

        // Delete file DB
        List<String> fileIds = chatFiles.stream().map(ChatFile::getFileId).collect(Collectors.toList());
        fileRepo.deleteAllById(fileIds);

        // Delete store path file
        deleteStorePathFile(groupId);
    }

    /**
     * Delete store path file
     * 
     * @param groupId
     * @throws IOException
     */
    private void deleteStorePathFile(String groupId) throws IOException {
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
        Path targetPath = FileUtil.getTargetPath(envRootUploadPath, FileClassify.CHAT.toString(), groupId, null);
        FileUtil.deletePath(targetPath);
    }
}
