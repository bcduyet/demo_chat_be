package co.jp.xeex.chat.domains.file.delete;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.service.ChatMessageService;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.entity.ChatFile;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.entity.TaskFile;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFileRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.TaskFileRepository;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteFileServiceImpl implements DeleteFileService {

    // Error keys
    private static final String DELETE_FILE_ERR_FILE_IS_NOT_EXISTED = "DELETE_FILE_ERR_FILE_IS_NOT_EXISTED";
    private static final String DELETE_FILE_ERR_PERMISSION_DENIED = "DELETE_FILE_ERR_PERMISSION_DENIED";

    // DI
    private UserRepository userRepo;
    private FileRepository fileRepo;
    private ChatMessageRepository chatMessageRepo;
    private ChatFileRepository chatFileRepo;
    private TaskFileRepository taskFileRepo;
    private ChatMessageService chatMessageService;
    private ChatMessageBroadcastService chatMessageSendService;
    private EnvironmentUtil environmentUtil;

    @Override
    public DeleteFileResponse execute(DeleteFileRequest in) throws BusinessException {
        // Validation file
        File file = fileRepo.findByStoreName(in.getStoreName());
        if (file == null) {
            throw new BusinessException(DELETE_FILE_ERR_FILE_IS_NOT_EXISTED, in.lang);
        }

        // Check permission user
        if (!in.requestBy.equals(file.getCreateBy())) {
            throw new BusinessException(DELETE_FILE_ERR_PERMISSION_DENIED, in.lang);
        }

        // Get target path
        Path targePath = getTargetPath(in, file.getCreateAt());

        // Delete store file
        Path filePath = targePath.resolve(in.getStoreName());
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), in.lang);
            }
        }

        // Delete file data
        deleteFileData(in);

        // Response
        DeleteFileResponse response = new DeleteFileResponse();
        response.setResult(true);
        return response;
    }

    /**
     * Get target path
     * 
     * @param in
     * @param fileTimestamp
     * @return
     * @throws BusinessException
     */
    private Path getTargetPath(DeleteFileRequest in, Timestamp fileTimestamp) throws BusinessException {
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);

        // Get requestPath
        String targetPath = in.requestBy;
        if (FileClassify.TEMP.equals(in.getFileClassify()) || FileClassify.AVATAR.equals(in.getFileClassify())) {
            return FileUtil.getTargetPath(envRootUploadPath, in.getFileClassify().toString(), targetPath, null);
        }

        // Get targetPath
        String groupId = fileRepo.getGroupByStoreName(in.getStoreName());
        targetPath = groupId == null ? targetPath : groupId;

        return FileUtil.getTargetPath(envRootUploadPath, in.getFileClassify().toString(), targetPath, fileTimestamp);
    }

    /**
     * Delete file data
     * 
     * @param in
     */
    private void deleteFileData(DeleteFileRequest in) {
        // Delete file data
        File file = fileRepo.findByStoreName(in.getStoreName());
        if (file == null) {
            return;
        }
        fileRepo.delete(file);

        if (FileClassify.AVATAR.equals(in.getFileClassify())) {
            // Remove avatar data
            User user = userRepo.findByUserName(in.requestBy);
            if (user != null) {
                user.setAvatar(null);
                userRepo.saveAndFlush(user);
            }
        } else if (FileClassify.CHAT.equals(in.getFileClassify())) {
            // Delete chat file data
            deleteChatFile(in, file);
        } else if (FileClassify.TASK.equals(in.getFileClassify())) {
            // Delete task file data
            TaskFile taskFile = taskFileRepo.findByFileId(file.getId());
            if (taskFile != null) {
                taskFileRepo.delete(taskFile);
            }
        }
    }

    /**
     * Delete chat file data
     * 
     * @param in
     * @param file
     */
    private void deleteChatFile(DeleteFileRequest in, File file) {
        // Delete chat file data
        ChatFile chatFile = chatFileRepo.findByFileId(file.getId());
        if (chatFile == null) {
            return;
        }
        chatFileRepo.delete(chatFile);

        // Notify to all user in group/friend
        ChatMessageDto notifyMessage = chatMessageService.getChatMessageDtoById(chatFile.getMessageId());
        notifyMessage.action = ChatAction.DELETE_FILE_CHAT;

        List<ChatFile> chatFiles = chatFileRepo.findAllByMessageId(chatFile.getMessageId());
        if (chatFiles.isEmpty()) {
            ChatMessage chatMessage = chatMessageRepo.findMessageEmpty(chatFile.getMessageId());
            if (chatMessage != null) {
                chatMessageService.deleteOrEditChatMessage(chatMessage, in.lang);
                notifyMessage.chatContent = chatMessage.getChatContent();
                notifyMessage.action = chatMessage.getAction();
            }
        }

        // Notify to all user in group/friend
        chatMessageSendService.broadcastMessageToGroup(notifyMessage);
    }
}
