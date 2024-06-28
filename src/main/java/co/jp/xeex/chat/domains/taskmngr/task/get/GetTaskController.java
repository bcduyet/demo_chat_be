package co.jp.xeex.chat.domains.taskmngr.task.get;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetTaskController
 * 
 * @author q_thinh
 */
@RestController
public class GetTaskController extends ControllerBase<GetTaskRequest> {
    private final GetTaskService getTaskService;

    public GetTaskController(@Autowired GetTaskService getTaskService) {
        this.getTaskService = getTaskService;
    }

    @PostMapping(RestApiEndPoints.GET_TASK)
    public final ResponseEntity<?> restApi(@RequestBody GetTaskRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetTaskResponse out = getTaskService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
