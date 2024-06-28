package co.jp.xeex.chat.domains.chatmngr.dept.getmbr;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetDeptMemberController
 * 
 * @author q_thinh
 */
@RestController
public class GetDeptMemberController extends ControllerBase<GetDeptMemberRequest> {
    private final GetDeptMemberService getDeptMemberService;

    public GetDeptMemberController(@Autowired GetDeptMemberService getDeptMemberService) {
        this.getDeptMemberService = getDeptMemberService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody GetDeptMemberRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.GET_DEPT_MEMBER)
    public final ResponseEntity<?> restApi2(GetDeptMemberRequest request) throws Exception {
        super.preProcessRequest(request);
        GetDeptMemberResponse out = getDeptMemberService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
