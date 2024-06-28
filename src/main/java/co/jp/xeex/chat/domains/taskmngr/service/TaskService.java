package co.jp.xeex.chat.domains.taskmngr.service;

import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import co.jp.xeex.chat.entity.Task;

/**
 * TaskService
 * 
 * @author q_thinh
 */
public interface TaskService {

    /**
     * Retrieves a TaskDto object based on the provided Task object.
     *
     * @param task The Task object to retrieve the TaskDto from.
     * @owner The owner of the task.
     * @isGetInfo Whether to get the task information.
     * @return The TaskDto object corresponding to the provided Task object.
     */
    public TaskDto getTaskDtoByTask(Task task, String owner, boolean isGetInfo);
}
