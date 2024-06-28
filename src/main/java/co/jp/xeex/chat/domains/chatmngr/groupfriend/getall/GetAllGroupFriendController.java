package co.jp.xeex.chat.domains.chatmngr.groupfriend.getall;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetAllGroupFriendController
 * 
 * @author q_thinh
 */
@RestController
public class GetAllGroupFriendController extends ControllerBase<GetAllGroupFriendRequest> {
    private final GetAllGroupFriendService getAllGroupFriendService;

    public GetAllGroupFriendController(@Autowired GetAllGroupFriendService getAllGroupFriendService) {
        this.getAllGroupFriendService = getAllGroupFriendService;
    }

    @PostMapping(RestApiEndPoints.GET_ALL_GROUP_FRIEND)
    public final ResponseEntity<?> restApi(@RequestBody GetAllGroupFriendRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetAllGroupFriendResponse out = getAllGroupFriendService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
