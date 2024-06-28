package co.jp.xeex.chat.domains.admin.search.user;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchUserController extends ControllerBase<SearchUserRequest> {
    private final SearchUserService service;

    public SearchUserController(@Autowired SearchUserService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.QUERY_USER)
    public final ResponseEntity<?> restApi(@RequestBody SearchUserRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SearchUserResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
