package co.jp.xeex.chat.domains.taskmngr.task.update.status;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UpdateTaskStatusController
 * 
 * @author q_thinh
 */
@RestController
public class UpdateTaskStatusController extends ControllerBase<UpdateTaskStatusRequest> {
    private final UpdateTaskStatusService updateTaskStatusService;

    public UpdateTaskStatusController(@Autowired UpdateTaskStatusService updateTaskStatusService) {
        this.updateTaskStatusService = updateTaskStatusService;
    }

    @PostMapping(RestApiEndPoints.UPDATE_TASK_STATUS)
    public final ResponseEntity<?> restApi(@RequestBody UpdateTaskStatusRequest request) throws BusinessException {
        super.preProcessRequest(request);
        UpdateTaskStatusResponse out = updateTaskStatusService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
