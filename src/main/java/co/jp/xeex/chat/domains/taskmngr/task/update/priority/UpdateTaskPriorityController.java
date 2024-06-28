package co.jp.xeex.chat.domains.taskmngr.task.update.priority;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UpdateTaskPriorityController
 * 
 * @author q_thinh
 */
@RestController
public class UpdateTaskPriorityController extends ControllerBase<UpdateTaskPriorityRequest> {
    private final UpdateTaskPriorityService updateTaskPriorityService;

    public UpdateTaskPriorityController(@Autowired UpdateTaskPriorityService updateTaskPriorityService) {
        this.updateTaskPriorityService = updateTaskPriorityService;
    }

    @PostMapping(RestApiEndPoints.UPDATE_TASK_PRIORITY)
    public final ResponseEntity<?> restApi(@RequestBody UpdateTaskPriorityRequest request) throws BusinessException {
        super.preProcessRequest(request);
        UpdateTaskPriorityResponse out = updateTaskPriorityService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
