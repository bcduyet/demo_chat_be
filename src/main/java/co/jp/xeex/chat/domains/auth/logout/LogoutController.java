package co.jp.xeex.chat.domains.auth.logout;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//only need to rename UseCase to the actual use case name
@AllArgsConstructor
@RestController
public class LogoutController extends ControllerBase<LogoutRequest> {
    private final LogoutService logoutService;

    @Override
    @PostMapping(RestApiEndPoints.LOGOUT)
    public final ResponseEntity<?> restApi(@RequestBody LogoutRequest request) throws BusinessException {
        super.preProcessRequest(request);
        LogoutResponse out = logoutService.execute(request);
        return out != null ? createSuccessResponse(null) : createNullFailResponse();
    }
}
