package co.jp.xeex.chat.domains.chatmngr.group.save;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SaveGroupController
 * 
 * @author q_thinh
 */
@RestController
public class SaveGroupController extends ControllerBase<SaveGroupRequest> {
    private final SaveGroupService saveGroupService;

    public SaveGroupController(@Autowired SaveGroupService saveGroupService) {
        this.saveGroupService = saveGroupService;
    }

    @Override
    @PostMapping(RestApiEndPoints.SAVE_GROUP)
    public final ResponseEntity<?> restApi(@RequestBody SaveGroupRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SaveGroupResponse out = saveGroupService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
