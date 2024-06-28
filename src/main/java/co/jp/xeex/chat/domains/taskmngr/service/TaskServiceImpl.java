package co.jp.xeex.chat.domains.taskmngr.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import com.mysql.cj.util.StringUtils;

import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.repply.mapper.ChatMessageMapper;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskStatus;
import co.jp.xeex.chat.domains.taskmngr.task.mapper.TaskMapper;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.Task;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import lombok.AllArgsConstructor;

/**
 * TaskServiceImpl
 * 
 * @author q_thinh
 */
@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    // DI
    private ChatMessageRepository chatMessageRepo;
    private UserRepository userRepo;
    private FileRepository fileRepo;
    private EnvironmentUtil environmentUtil;
    private TaskMapper taskMapper;
    private ChatMessageMapper chatMessageMapper;

    /**
     * Get task DTO data by task
     * 
     * @param task
     * @param owner
     * @param isGetInfo
     */
    @Override
    public TaskDto getTaskDtoByTask(Task task, String owner, boolean isGetInfo) {
        TaskDto resultTaskDto = taskMapper.taskToDto(task);

        // Setting full name
        User user = userRepo.findByUserName(task.getCreateBy());
        resultTaskDto.setRequestByFullName(user.getFullName());
        if (!StringUtils.isNullOrEmpty(task.getAssignee())) {
            user = userRepo.findByUserName(task.getAssignee());
            resultTaskDto.setAssigneeFullName(user.getFullName());
        }

        // Setting task file info
        resultTaskDto.setTaskFiles(new ArrayList<>());
        if (isGetInfo) {
            resultTaskDto.setTaskFiles(getTaskFilesByTaskId(task.getId()));
        }

        // Setting chat message info
        resultTaskDto.setChatMessage(getChatMessageDtoByTask(task.getId()));

        // Setting lock edit/delete
        boolean isOwner = owner.equals(task.getAssignee()) || owner.equals(task.getCreateBy());
        resultTaskDto.setIsLockEdit(TaskStatus.DONE.equals(task.getTaskStatus()) || !isOwner);
        resultTaskDto.setIsOwner(isOwner);

        return resultTaskDto;
    }

    /**
     * Get task data by id
     * 
     * @param taskId
     * @return
     */
    private ChatMessageDto getChatMessageDtoByTask(String taskId) {
        ChatMessageDto chatMessageDto = null;

        ChatMessage chatMessage = chatMessageRepo.findByTaskId(taskId);
        if (chatMessage != null) {
            List<File> files = fileRepo.findByMessageId(chatMessage.getId());
            List<FileDto> chatFiles = new ArrayList<>();
            String domain = environmentUtil.getDomain();
            for (File file : files) {
                FileDto fileDto = new FileDto();
                fileDto.setOriginName(file.getOriginName());
                fileDto.setStoreName(file.getStoreName());
                fileDto.setFileType(file.getFileType());
                fileDto.setEmpCd(file.getCreateBy());

                // Setting download url
                String fileUrl = String.format(AppConstant.FILE_URL, domain, AppConstant.PATH_CHAT_PREFIX,
                        fileDto.getStoreName());
                fileDto.setDownloadUrl(fileUrl);

                chatFiles.add(fileDto);
            }

            chatMessageDto = chatMessageMapper.chatMessageToDto(chatMessage);
            chatMessageDto.chatFiles = chatFiles;
        }

        return chatMessageDto;
    }

    /**
     * Get task files by task id
     * 
     * @param taskId
     * @return
     */
    private List<FileDto> getTaskFilesByTaskId(String taskId) {
        List<File> files = fileRepo.findByTaskId(taskId);

        List<FileDto> taskFiles = new ArrayList<>();
        for (File file : files) {
            FileDto fileDto = new FileDto();
            fileDto.setOriginName(file.getOriginName());
            fileDto.setStoreName(file.getStoreName());
            fileDto.setFileType(file.getFileType());
            fileDto.setEmpCd(file.getCreateBy());

            // Setting download url
            String domain = environmentUtil.getDomain();
            String fileUrl = String.format(AppConstant.FILE_URL, domain, AppConstant.PATH_TASK_PREFIX,
                    fileDto.getStoreName());
            fileDto.setDownloadUrl(fileUrl);
            taskFiles.add(fileDto);
        }

        return taskFiles;
    }
}
