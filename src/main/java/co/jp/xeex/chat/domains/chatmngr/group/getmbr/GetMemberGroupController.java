package co.jp.xeex.chat.domains.chatmngr.group.getmbr;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetMemberGroupController
 * 
 * @author q_thinh
 */
@RestController
public class GetMemberGroupController extends ControllerBase<GetMemberGroupRequest> {
    private final GetMemberGroupService getMemberGroupService;

    public GetMemberGroupController(@Autowired GetMemberGroupService getMemberGroupService) {
        this.getMemberGroupService = getMemberGroupService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody GetMemberGroupRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.GET_MEMBER_GROUP)
    public final ResponseEntity<?> restApi2(GetMemberGroupRequest request) throws Exception {
        super.preProcessRequest(request);
        GetMemberGroupResponse out = getMemberGroupService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
