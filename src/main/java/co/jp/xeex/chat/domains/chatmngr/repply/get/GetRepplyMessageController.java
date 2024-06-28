package co.jp.xeex.chat.domains.chatmngr.repply.get;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetRepplyMessageController
 * 
 * @author q_thinh
 */
@RestController
public class GetRepplyMessageController extends ControllerBase<GetRepplyMessageRequest> {
    private final GetRepplyMessageService getRepplyMessageService;

    public GetRepplyMessageController(@Autowired GetRepplyMessageService getRepplyMessageService) {
        this.getRepplyMessageService = getRepplyMessageService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody GetRepplyMessageRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.GET_REPPLY_HISTORY)
    public final ResponseEntity<?> restApi2(GetRepplyMessageRequest request) throws Exception {
        super.preProcessRequest(request);
        GetRepplyMessageResponse out = getRepplyMessageService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
