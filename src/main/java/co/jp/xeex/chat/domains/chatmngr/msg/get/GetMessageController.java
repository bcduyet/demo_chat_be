package co.jp.xeex.chat.domains.chatmngr.msg.get;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * GetMessageController
 * 
 * @author q_thinh
 */
@RestController
public class GetMessageController extends ControllerBase<GetMessageRequest> {
    private final GetMessageService getMessageService;

    public GetMessageController(@Autowired GetMessageService getMessageService) {
        this.getMessageService = getMessageService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody GetMessageRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @GetMapping(RestApiEndPoints.GET_MESSAGE_HISTORY)
    public final ResponseEntity<?> restApi2(GetMessageRequest request) throws Exception {
        super.preProcessRequest(request);
        GetMessageResponse out = getMessageService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
