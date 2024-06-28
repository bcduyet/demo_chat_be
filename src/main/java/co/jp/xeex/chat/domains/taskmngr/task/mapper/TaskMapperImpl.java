package co.jp.xeex.chat.domains.taskmngr.task.mapper;

import org.springframework.stereotype.Service;

import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import co.jp.xeex.chat.entity.Task;

/**
 * TaskMapperImpl
 * 
 * @author q_thinh
 */
@Service
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskDto taskToDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setGroupId(task.getGroupId());
        taskDto.setTaskId(task.getId());
        taskDto.setTaskName(task.getTaskName());
        taskDto.setStartDate(task.getStartDate() == null ? null : task.getStartDate().toString());
        taskDto.setDueDate(task.getDueDate() == null ? null : task.getDueDate().toString());
        taskDto.setAssignee(task.getAssignee());
        taskDto.setDescription(task.getDescription());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setTaskPriority(task.getTaskPriority());
        taskDto.requestBy = task.getCreateBy();
        return taskDto;
    }
}
