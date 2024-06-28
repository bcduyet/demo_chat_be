package co.jp.xeex.chat.domains.admin.save.user;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaveUserController extends ControllerBase<SaveUserRequest> {
    private final SaveUserService service;

    public SaveUserController(@Autowired SaveUserService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.SAVE_USER)
    public ResponseEntity<?> restApi(@RequestBody SaveUserRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SaveUserResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
