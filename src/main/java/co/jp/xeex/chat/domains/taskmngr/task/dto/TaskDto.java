package co.jp.xeex.chat.domains.taskmngr.task.dto;

import java.util.List;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskPriority;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TaskDto
 * Common TaskDto response to client
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TaskDto extends RequestBase {
    private String requestByFullName;
    private String groupId;
    private ChatMessageDto chatMessage;
    private String taskId;
    private String taskName;
    private String startDate;
    private String dueDate;
    private String assignee;
    private String assigneeFullName;
    private String description;
    private TaskStatus taskStatus;
    private TaskPriority taskPriority;
    private List<FileDto> taskFiles;
    private Boolean isLockEdit;
    private Boolean isOwner;
}
