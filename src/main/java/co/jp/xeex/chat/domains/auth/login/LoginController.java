package co.jp.xeex.chat.domains.auth.login;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController extends ControllerBase<LoginRequest> {
    private final LoginService service;

    public LoginController(@Autowired LoginService service) {
        this.service = service;
    }

    @Override
    @PostMapping(RestApiEndPoints.LOGIN)
    public final ResponseEntity<?> restApi(@RequestBody LoginRequest request) throws BusinessException {
        super.preProcessRequest(request);
        LoginResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
