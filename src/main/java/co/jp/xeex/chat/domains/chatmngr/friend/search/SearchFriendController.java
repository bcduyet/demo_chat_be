package co.jp.xeex.chat.domains.chatmngr.friend.search;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchFriendController
 * 
 * @author q_thinh
 */
@RestController
public class SearchFriendController extends ControllerBase<SearchFriendRequest> {
    private final SearchFriendService searchFriendService;

    public SearchFriendController(@Autowired SearchFriendService searchFriendService) {
        this.searchFriendService = searchFriendService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody SearchFriendRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.SEARCH_FRIEND)
    public final ResponseEntity<?> restApi2(SearchFriendRequest request) throws Exception {
        super.preProcessRequest(request);
        SearchFriendResponse out = searchFriendService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
