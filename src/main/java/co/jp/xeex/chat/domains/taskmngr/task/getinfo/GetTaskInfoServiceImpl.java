package co.jp.xeex.chat.domains.taskmngr.task.getinfo;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.taskmngr.service.TaskService;
import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import co.jp.xeex.chat.entity.Task;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.TaskRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * GetTaskInfoServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class GetTaskInfoServiceImpl extends ServiceBaseImpl<GetTaskInfoRequest, GetTaskInfoResponse>
        implements GetTaskInfoService {

    // Error keys
    private static final String GET_TASK_INFO_ERR_TASK_IS_NOT_EXISTED = "GET_TASK_INFO_ERR_TASK_IS_NOT_EXISTED";

    // DI
    private TaskRepository taskRepo;
    private TaskService taskService;

    @Override
    public GetTaskInfoResponse processRequest(GetTaskInfoRequest in) throws BusinessException {
        // Get task by id
        Task task = taskRepo.findById(in.taskId).orElse(null);
        if (task == null) {
            throw new BusinessException(GET_TASK_INFO_ERR_TASK_IS_NOT_EXISTED, in.lang);
        }

        // Mapping to task DTO
        TaskDto taskDto = taskService.getTaskDtoByTask(task, in.requestBy, true);

        // Response
        GetTaskInfoResponse response = new GetTaskInfoResponse();
        response.setTask(taskDto);
        return response;
    }
}
