package co.jp.xeex.chat.domains.taskmngr.task.get;

import co.jp.xeex.chat.base.PageInfo;
import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.taskmngr.service.TaskService;
import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import co.jp.xeex.chat.entity.Task;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.TaskRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * GetTaskServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class GetTaskServiceImpl extends ServiceBaseImpl<GetTaskRequest, GetTaskResponse>
        implements GetTaskService {

    // DI
    private TaskRepository taskRepo;
    private ChatGroupRepository chatGroupRepo;
    private TaskService taskService;

    @Override
    public GetTaskResponse processRequest(GetTaskRequest in) throws BusinessException {
        // v_long: refactoring create page
        PageInfo page = new PageInfo(in.page, in.perPage, in.orderBys);

        // Get task data
        List<String> groupIds = chatGroupRepo.getUserGroupIdList(in.requestBy);
        Page<Task> pageTasks = taskRepo.getTasksByValue(groupIds, in.groupId, in.requestBy, in.searchValue,
                page.getPageRequest());
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : pageTasks) {
            TaskDto taskDto = taskService.getTaskDtoByTask(task, in.requestBy, false);
            taskDtos.add(taskDto);
        }

        // Response
        GetTaskResponse response = new GetTaskResponse();
        response.setTotalTask(taskRepo.countByValue(groupIds, in.groupId, in.requestBy, in.searchValue));
        response.setTasks(taskDtos);
        return response;
    }
}
