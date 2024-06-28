package co.jp.xeex.chat.domains.chatmngr.msg.delete;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.service.ChatMessageService;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.entity.ChatFile;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.entity.MessageTask;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFileRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.MessageTaskRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * DeleteMessageServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class DeleteMessageServiceImpl extends ServiceBaseImpl<DeleteMessageRequest, DeleteMessageResponse>
        implements DeleteMessageService {

    // Error key
    private static final String DELETE_MESSAGE_ERR_MESSAGE_IS_NOT_EXIST = "DELETE_MESSAGE_ERR_MESSAGE_IS_NOT_EXIST";
    private static final String DELETE_MESSAGE_ERR_PERMISSION_DENIED = "DELETE_MESSAGE_ERR_PERMISSION_DENIED";

    // DI
    private ChatMessageRepository chatMessageRepo;
    private ChatFileRepository chatFileRepo;
    private FileRepository fileRepo;
    private MessageTaskRepository messageTaskRepo;
    private EnvironmentUtil environmentUtil;
    private ChatMessageService chatMessageService;
    private ChatMessageBroadcastService chatMessageSendService;

    @Override
    public DeleteMessageResponse processRequest(DeleteMessageRequest in) throws BusinessException {
        // Get messages
        ChatMessage chatMessage = chatMessageRepo.findById(in.messageId).orElse(null);
        if (chatMessage == null) {
            throw new BusinessException(DELETE_MESSAGE_ERR_MESSAGE_IS_NOT_EXIST, in.lang);
        }

        // Check permission user
        if (!in.requestBy.equals(chatMessage.getCreateBy())) {
            throw new BusinessException(DELETE_MESSAGE_ERR_PERMISSION_DENIED, in.lang);
        }

        // Delete chat files
        try {
            deleteChatFile(chatMessage);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), in.lang);
        }

        // Delete task mapping
        MessageTask messageTask = messageTaskRepo.findByMessageId(chatMessage.getId());
        if (messageTask != null) {
            messageTaskRepo.delete(messageTask);
        }

        // delete or edit chat message
        chatMessageService.deleteOrEditChatMessage(chatMessage, in.lang);

        // Notify to all user in group/friend
        ChatMessageDto notifyMessage = new ChatMessageDto();
        notifyMessage.groupId = chatMessage.getGroupId();
        notifyMessage.messageId = chatMessage.getId();
        notifyMessage.repplyMessageId = chatMessage.getRepplyMessageId();
        notifyMessage.chatContent = chatMessage.getChatContent();
        notifyMessage.requestBy = in.requestBy;
        notifyMessage.lang = in.lang;
        notifyMessage.action = chatMessage.getAction();
        chatMessageSendService.broadcastMessageToGroup(notifyMessage);

        // Response
        DeleteMessageResponse response = new DeleteMessageResponse();
        response.setResult(true);
        return response;
    }

    /**
     * Delete chat files
     * 
     * @param chatMessage
     * @throws IOException
     */
    private void deleteChatFile(ChatMessage chatMessage) throws IOException {
        // Delete chat files
        List<ChatFile> chatFiles = chatFileRepo.findAllByMessageId(chatMessage.getId());
        List<String> chatFileIds = chatFiles.stream().map(ChatFile::getId).collect(Collectors.toList());
        chatFileRepo.deleteAllById(chatFileIds);

        List<String> fileIds = chatFiles.stream().map(ChatFile::getFileId).collect(Collectors.toList());
        List<File> files = fileRepo.findAllById(fileIds);
        fileRepo.deleteAllById(fileIds);

        // Delete store file
        deleteStoreFile(files, chatMessage.getGroupId());
    }

    /**
     * Delete store file
     * 
     * @param files
     * @param groupId
     * @throws IOException
     */
    private void deleteStoreFile(List<File> files, String groupId) throws IOException {
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
        for (File file : files) {
            Path targetPath = FileUtil.getTargetPath(envRootUploadPath, FileClassify.CHAT.toString(), groupId,
                    file.getCreateAt());

            // Delete temp path
            targetPath = targetPath.resolve(Paths.get(file.getStoreName())).normalize().toAbsolutePath();
            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
            }
        }
    }
}
