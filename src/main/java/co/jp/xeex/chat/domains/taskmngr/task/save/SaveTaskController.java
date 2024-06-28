package co.jp.xeex.chat.domains.taskmngr.task.save;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SaveTaskController
 * 
 * @author q_thinh
 */
@RestController
public class SaveTaskController extends ControllerBase<SaveTaskRequest> {
    private final SaveTaskService saveTaskService;

    public SaveTaskController(@Autowired SaveTaskService saveTaskService) {
        this.saveTaskService = saveTaskService;
    }

    @PostMapping(RestApiEndPoints.SAVE_TASK)
    public final ResponseEntity<?> restApi(@RequestBody SaveTaskRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SaveTaskResponse out = saveTaskService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
