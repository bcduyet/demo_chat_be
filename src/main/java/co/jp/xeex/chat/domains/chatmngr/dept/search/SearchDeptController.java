package co.jp.xeex.chat.domains.chatmngr.dept.search;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SearchDeptController
 * 
 * @author q_thinh
 */
@RestController
public class SearchDeptController extends ControllerBase<SearchDeptRequest> {
    private final SearchDeptService searchDeptService;

    public SearchDeptController(@Autowired SearchDeptService searchDeptService) {
        this.searchDeptService = searchDeptService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody SearchDeptRequest request) throws BusinessException{
        return null;
    }
    @GetMapping(RestApiEndPoints.SEARCH_DEPT)
    public final ResponseEntity<?> restApi2(SearchDeptRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SearchDeptResponse out = searchDeptService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
