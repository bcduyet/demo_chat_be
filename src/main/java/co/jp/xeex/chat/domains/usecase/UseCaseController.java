package co.jp.xeex.chat.domains.usecase;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//only need to rename UseCase to the actual use case name

@RestController
public class UseCaseController extends ControllerBase<UseCaseRequest> {
    private final UseCaseService service;

    public UseCaseController(@Autowired UseCaseService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.USE_CASE)
    public final ResponseEntity<?> restApi(@RequestBody UseCaseRequest request) throws BusinessException {
        super.preProcessRequest(request);
        UseCaseResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
