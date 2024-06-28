package co.jp.xeex.chat.domains.taskmngr.task.delete;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageBroadcastService;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.repply.mapper.ChatMessageMapper;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.entity.MessageTask;
import co.jp.xeex.chat.entity.Task;
import co.jp.xeex.chat.entity.TaskFile;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.MessageTaskRepository;
import co.jp.xeex.chat.repository.TaskFileRepository;
import co.jp.xeex.chat.repository.TaskRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DeleteTaskServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class DeleteTaskServiceImpl extends ServiceBaseImpl<DeleteTaskRequest, DeleteTaskResponse>
        implements DeleteTaskService {

    // Error keys
    private static final String DELETE_TASK_ERR_TASK_IS_NOT_EXISTED = "DELETE_TASK_ERR_TASK_IS_NOT_EXISTED";
    private static final String DELETE_TASK_ERR_PERMISSION_DENIED = "DELETE_TASK_ERR_PERMISSION_DENIED";

    // DI
    private TaskRepository taskRepo;
    private TaskFileRepository taskFileRepo;
    private FileRepository fileRepo;
    private MessageTaskRepository messageTaskRepo;
    private ChatMessageBroadcastService chatMessageSendService;
    private ChatMessageRepository chatMessageRepo;
    private ChatMessageMapper chatMessageMapper;
    private EnvironmentUtil environmentUtil;

    @Override
    public DeleteTaskResponse processRequest(DeleteTaskRequest in) throws BusinessException {
        // Get task
        Task task = taskRepo.findById(in.taskId).orElse(null);
        if (task == null) {
            throw new BusinessException(DELETE_TASK_ERR_TASK_IS_NOT_EXISTED, in.lang);
        }

        // Check permission user
        if (!in.requestBy.equals(task.getCreateBy()) && !in.requestBy.equals(task.getAssignee())) {
            throw new BusinessException(DELETE_TASK_ERR_PERMISSION_DENIED, in.lang);
        }

        // Delete task files
        try {
            deleteTaskFile(task);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), in.lang);
        }

        // Delete task mapping
        MessageTask messageTask = messageTaskRepo.findByTaskId(task.getId());
        if (messageTask != null) {
            messageTaskRepo.delete(messageTask);

            // Notify to all user in group/friend
            ChatMessage chatMessage = chatMessageRepo.findByTaskId(messageTask.getTaskId());
            if (chatMessage != null) {
                ChatMessageDto notifyMessage = chatMessageMapper.chatMessageToDto(chatMessage);
                notifyMessage.action = ChatAction.DELETE_TASK_MESSAGE;
                chatMessageSendService.broadcastMessageToGroup(notifyMessage);
            }
        }

        // Delete task
        taskRepo.delete(task);

        // Response
        DeleteTaskResponse response = new DeleteTaskResponse();
        response.setResult(true);
        return response;
    }

    /**
     * Delete task files
     * 
     * @param task
     * @throws IOException
     */
    private void deleteTaskFile(Task task) throws IOException {
        // Delete task files
        List<TaskFile> taskFiles = taskFileRepo.findAllByTaskId(task.getId());
        List<String> taskFileIds = taskFiles.stream().map(TaskFile::getId).collect(Collectors.toList());
        taskFileRepo.deleteAllById(taskFileIds);

        // Delete files
        List<String> fileIds = taskFiles.stream().map(TaskFile::getFileId).collect(Collectors.toList());
        List<File> files = fileRepo.findAllById(fileIds);
        fileRepo.deleteAllById(fileIds);

        // Delete store file
        deleteStoreFile(files, (task.getGroupId() == null) ? task.getCreateBy() : task.getGroupId());
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
            Path targetPath = FileUtil.getTargetPath(envRootUploadPath, FileClassify.TASK.toString(), groupId,
                    file.getCreateAt());

            // Delete temp path
            targetPath = targetPath.resolve(Paths.get(file.getStoreName())).normalize().toAbsolutePath();
            Files.delete(targetPath);
        }
    }
}
