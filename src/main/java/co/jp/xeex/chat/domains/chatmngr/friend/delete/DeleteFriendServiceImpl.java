package co.jp.xeex.chat.domains.chatmngr.friend.delete;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.entity.ChatFile;
import co.jp.xeex.chat.entity.ChatFriend;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFileRepository;
import co.jp.xeex.chat.repository.ChatFriendRepository;
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
 * DeleteFriendServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class DeleteFriendServiceImpl extends ServiceBaseImpl<DeleteFriendRequest, DeleteFriendResponse>
        implements DeleteFriendService {

    // Error keys
    private static final String DELETE_FRIEND_ERR_FRIEND_IS_NOT_EXISTED = "DELETE_FRIEND_ERR_FRIEND_IS_NOT_EXISTED";

    // DI
    private ChatGroupRepository chatGroupRepo;
    private ChatFriendRepository chatFriendRepo;
    private ChatMessageRepository chatMessageRepo;
    private ChatFileRepository chatFileRepo;
    private FileRepository fileRepo;
    private MessageTaskRepository messageTaskRepo;
    private ChatMessageBroadcastService chatMessageSendService;
    private EnvironmentUtil environmentUtil;

    @Override
    @Transactional
    public DeleteFriendResponse processRequest(DeleteFriendRequest in) throws BusinessException {
        // Get friends
        List<ChatFriend> chatFriends = chatFriendRepo.findByCd(in.requestBy, in.friendCd);
        if (chatFriends.isEmpty()) {
            throw new BusinessException(DELETE_FRIEND_ERR_FRIEND_IS_NOT_EXISTED, in.lang);
        }

        // Delete friend
        String groupId = chatFriends.get(0).getGroupId();
        for (ChatFriend chatFriend : chatFriends) {
            chatFriendRepo.delete(chatFriend);
        }

        // Delete chat files
        try {
            deleteChatFile(groupId);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), in.lang);
        }

        // Delete message task
        List<String> messageTaskIds = messageTaskRepo.getListIdByGroupId(groupId);
        messageTaskRepo.deleteAllById(messageTaskIds);

        // Delete chat message
        List<String> chatMessageIds = chatMessageRepo.getListIdByGroup(groupId);
        chatMessageRepo.deleteAllById(chatMessageIds);

        // Delete chat group
        chatGroupRepo.findById(groupId).ifPresent(chatGroupRepo::delete);

        // Send notify to user
        ChatMessageDto notifyMessage = new ChatMessageDto();
        notifyMessage.groupId = groupId;
        notifyMessage.chatContent = in.friendCd;
        notifyMessage.messageId = "-1";
        notifyMessage.requestBy = in.requestBy;
        notifyMessage.lang = in.lang;
        notifyMessage.action = ChatAction.DELETE_FRIEND;
        chatMessageSendService.broadcastMessageToUser(notifyMessage, in.friendCd);

        // Response
        DeleteFriendResponse response = new DeleteFriendResponse();
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
