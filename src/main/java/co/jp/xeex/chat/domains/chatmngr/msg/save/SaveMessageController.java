package co.jp.xeex.chat.domains.chatmngr.msg.save;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * SaveMessageController
 * 
 * @author q_thinh
 */
@RestController
public class SaveMessageController extends ControllerBase<SaveMessageRequest> {
    private final SaveMessageService saveMessageService;

    public SaveMessageController(@Autowired SaveMessageService saveMessageService) {
        this.saveMessageService = saveMessageService;
    }

    @Override
    @PostMapping(RestApiEndPoints.SAVE_MESSAGE)
    public final ResponseEntity<?> restApi(@RequestBody SaveMessageRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SaveMessageResponse out = saveMessageService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
