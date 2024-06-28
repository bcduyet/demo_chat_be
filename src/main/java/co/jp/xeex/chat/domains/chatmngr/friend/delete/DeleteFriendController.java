package co.jp.xeex.chat.domains.chatmngr.friend.delete;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeleteFriendController
 * 
 * @author q_thinh
 */
@RestController
public class DeleteFriendController extends ControllerBase<DeleteFriendRequest> {
    private final DeleteFriendService deleteFriendService;

    public DeleteFriendController(@Autowired DeleteFriendService deleteFriendService) {
        this.deleteFriendService = deleteFriendService;
    }

    @Override
    @PostMapping(RestApiEndPoints.DELETE_FRIEND)
    public final ResponseEntity<?> restApi(@RequestBody DeleteFriendRequest request) throws BusinessException {
        super.preProcessRequest(request);
        DeleteFriendResponse out = deleteFriendService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
