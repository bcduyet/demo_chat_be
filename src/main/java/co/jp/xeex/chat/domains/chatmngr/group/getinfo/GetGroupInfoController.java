package co.jp.xeex.chat.domains.chatmngr.group.getinfo;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetGroupInfoController
 * 
 * @author q_thinh
 */
@RestController
public class GetGroupInfoController extends ControllerBase<GetGroupInfoRequest> {
    private final GetGroupInfoService getGroupInfoService;

    public GetGroupInfoController(@Autowired GetGroupInfoService getGroupInfoService) {
        this.getGroupInfoService = getGroupInfoService;
    }

    @Override
    @PostMapping(RestApiEndPoints.GET_GROUP_INFO)
    public final ResponseEntity<?> restApi(@RequestBody GetGroupInfoRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetGroupInfoResponse out = getGroupInfoService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
