package co.jp.xeex.chat.domains.chatmngr.friend.getnone;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetNoneFriendMemberController
 * 
 * @author q_thinh
 */
@RestController
public class GetNoneFriendMemberController extends ControllerBase<GetNoneFriendMemberRequest> {
    private final GetNoneFriendMemberService getNoneFriendMemberService;

    public GetNoneFriendMemberController(@Autowired GetNoneFriendMemberService getNoneFriendMemberService) {
        this.getNoneFriendMemberService = getNoneFriendMemberService;
    }

    @PostMapping(RestApiEndPoints.GET_NONE_FRIEND_MEMBER)
    public final ResponseEntity<?> restApi(@RequestBody GetNoneFriendMemberRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetNoneFriendMemberResponse out = getNoneFriendMemberService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
