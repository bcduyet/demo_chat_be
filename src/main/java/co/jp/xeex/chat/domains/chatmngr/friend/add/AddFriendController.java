package co.jp.xeex.chat.domains.chatmngr.friend.add;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * AddFriendController
 * 
 * @author q_thinh
 */
@RestController
public class AddFriendController extends ControllerBase<AddFriendRequest> {
    private final AddFriendService addFriendService;

    public AddFriendController(@Autowired AddFriendService addFriendService) {
        this.addFriendService = addFriendService;
    }

    @Override
    @PostMapping(RestApiEndPoints.ADD_FRIEND)
    public final ResponseEntity<?> restApi(@RequestBody AddFriendRequest request) throws BusinessException {
        super.preProcessRequest(request);
        AddFriendResponse out = addFriendService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
