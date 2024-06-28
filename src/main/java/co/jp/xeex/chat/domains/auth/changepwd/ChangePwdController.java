package co.jp.xeex.chat.domains.auth.changepwd;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePwdController extends ControllerBase<ChangePwdRequest> {
    private final ChangePwdService service;

    public ChangePwdController(ChangePwdService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.CHANGE_PWD)
    public ResponseEntity<?> restApi(ChangePwdRequest request) throws BusinessException {
        super.preProcessRequest(request);
        ChangePwdResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }

}
