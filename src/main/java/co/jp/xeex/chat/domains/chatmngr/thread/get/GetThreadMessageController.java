package co.jp.xeex.chat.domains.chatmngr.thread.get;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetThreadMessageController
 * 
 * @author q_thinh
 */
@RestController
public class GetThreadMessageController extends ControllerBase<GetThreadMessageRequest> {
    private final GetThreadMessageService getThreadMessageService;

    public GetThreadMessageController(@Autowired GetThreadMessageService getThreadMessageService) {
        this.getThreadMessageService = getThreadMessageService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody GetThreadMessageRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.GET_THREAD_HISTORY)
    public final ResponseEntity<?> restApi2(GetThreadMessageRequest request) throws Exception {
        super.preProcessRequest(request);
        GetThreadMessageResponse out = getThreadMessageService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
