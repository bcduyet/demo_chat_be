package co.jp.xeex.chat.domains.taskmngr.task.update.priority;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskStatus;
import co.jp.xeex.chat.entity.Task;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.TaskRepository;

import org.springframework.stereotype.Service;

/**
 * UpdateTaskPriorityServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class UpdateTaskPriorityServiceImpl
        extends ServiceBaseImpl<UpdateTaskPriorityRequest, UpdateTaskPriorityResponse>
        implements UpdateTaskPriorityService {

    // Error keys
    private static final String UPDATE_TASK_PRIORITY_ERR_TASK_IS_NOT_EXISTED = "UPDATE_TASK_PRIORITY_ERR_TASK_IS_NOT_EXISTED";
    private static final String UPDATE_TASK_PRIORITY_ERR_CAN_NOT_EDIT_DONE_TASK = "UPDATE_TASK_PRIORITY_ERR_CAN_NOT_EDIT_DONE_TASK";
    private static final String UPDATE_TASK_PRIORITY_ERR_PERMISSION_DENIED = "UPDATE_TASK_PRIORITY_ERR_PERMISSION_DENIED";

    // DI
    private final TaskRepository taskRepo;

    public UpdateTaskPriorityServiceImpl(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public UpdateTaskPriorityResponse processRequest(UpdateTaskPriorityRequest in) throws BusinessException {
        // Get task
        Task task = taskRepo.findById(in.taskId).orElse(null);
        if (task == null) {
            throw new BusinessException(UPDATE_TASK_PRIORITY_ERR_TASK_IS_NOT_EXISTED, in.lang);
        }

        // Lock edit DONE status task
        if (TaskStatus.DONE.equals(task.getTaskStatus())) {
            throw new BusinessException(UPDATE_TASK_PRIORITY_ERR_CAN_NOT_EDIT_DONE_TASK, in.lang);
        }

        // Check permission user
        if (!in.requestBy.equals(task.getCreateBy()) && !in.requestBy.equals(task.getAssignee())) {
            throw new BusinessException(UPDATE_TASK_PRIORITY_ERR_PERMISSION_DENIED, in.lang);
        }

        // Update priority
        task.setTaskPriority(in.taskPriority);
        taskRepo.saveAndFlush(task);

        // Response
        UpdateTaskPriorityResponse response = new UpdateTaskPriorityResponse();
        response.setResult(true);
        return response;
    }
}
