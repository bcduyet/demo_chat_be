package co.jp.xeex.chat.domains.chatmngr.group.search;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchGroupController
 * 
 * @author q_thinh
 */
@RestController
public class SearchGroupController extends ControllerBase<SearchGroupRequest> {
    private final SearchGroupService searchGroupService;

    public SearchGroupController(@Autowired SearchGroupService searchGroupService) {
        this.searchGroupService = searchGroupService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody SearchGroupRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.SEARCH_GROUP)
    public final ResponseEntity<?> restApi2(SearchGroupRequest request) throws Exception {
        super.preProcessRequest(request);
        SearchGroupResponse out = searchGroupService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
