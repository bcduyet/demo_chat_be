package co.jp.xeex.chat.domains.chatmngr.group.delete;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeleteGroupController
 * 
 * @author q_thinh
 */
@RestController
public class DeleteGroupController extends ControllerBase<DeleteGroupRequest> {
    private final DeleteGroupService deleteGroupService;

    public DeleteGroupController(@Autowired DeleteGroupService deleteGroupService) {
        this.deleteGroupService = deleteGroupService;
    }

    @Override
    @PostMapping(RestApiEndPoints.DELETE_GROUP)
    public final ResponseEntity<?> restApi(@RequestBody DeleteGroupRequest request) throws BusinessException {
        super.preProcessRequest(request);
        DeleteGroupResponse out = deleteGroupService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
