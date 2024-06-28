package co.jp.xeex.chat.domains.taskmngr.task.getinfo;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetTaskInfoController
 * 
 * @author q_thinh
 */
@RestController
public class GetTaskInfoController extends ControllerBase<GetTaskInfoRequest> {
    private final GetTaskInfoService getTaskInfoService;

    public GetTaskInfoController(@Autowired GetTaskInfoService getTaskInfoService) {
        this.getTaskInfoService = getTaskInfoService;
    }

    @PostMapping(RestApiEndPoints.GET_TASK_INFO)
    public final ResponseEntity<?> restApi(@RequestBody GetTaskInfoRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetTaskInfoResponse out = getTaskInfoService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
