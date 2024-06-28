package co.jp.xeex.chat.domains.taskmngr.task.delete;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeleteTaskController
 * 
 * @author q_thinh
 */
@RestController
public class DeleteTaskController extends ControllerBase<DeleteTaskRequest> {
    private final DeleteTaskService deleteTaskService;

    public DeleteTaskController(@Autowired DeleteTaskService deleteTaskService) {
        this.deleteTaskService = deleteTaskService;
    }

    @PostMapping(RestApiEndPoints.DELETE_TASK)
    public final ResponseEntity<?> restApi(@RequestBody DeleteTaskRequest request) throws BusinessException {
        super.preProcessRequest(request);
        DeleteTaskResponse out = deleteTaskService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
