package co.jp.xeex.chat.domains.chatmngr.msg.delete;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * DeleteMessageController
 * 
 * @author q_thinh
 */
@RestController
public class DeleteMessageController extends ControllerBase<DeleteMessageRequest> {
    private final DeleteMessageService deleteMessageService;

    public DeleteMessageController(@Autowired DeleteMessageService deleteMessageService) {
        this.deleteMessageService = deleteMessageService;
    }

    @Override
    @PostMapping(RestApiEndPoints.DELETE_MESSAGE_HISTORY)
    public final ResponseEntity<?> restApi(@RequestBody DeleteMessageRequest request) throws BusinessException {
        super.preProcessRequest(request);
        DeleteMessageResponse out = deleteMessageService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
