package co.jp.xeex.chat.lang.resource;

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
public class ResourceMessageController extends ControllerBase<ResourceMessageRequestDto> {
    private final ResourceMessageService service;

    public ResourceMessageController(@Autowired ResourceMessageService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.LANG_MSG)
    public final ResponseEntity<?> restApi(@RequestBody ResourceMessageRequestDto request) throws BusinessException {
        super.preProcessRequest(request);
        ResourceMessageResponseDto out = service.execute(request);
        ResponseEntity<?> response = out != null ? createSuccessResponse(out) : createNullFailResponse();
        return response;
    }
}
