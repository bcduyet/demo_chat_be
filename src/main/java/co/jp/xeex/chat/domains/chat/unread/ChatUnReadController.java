package co.jp.xeex.chat.domains.chat.unread;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Rest controller for the chat unread messages
 * 
 * @author v_long
 */
@RestController
@AllArgsConstructor
public class ChatUnReadController extends ControllerBase<GetChatUnreadRequest> {
    private ChatUnreadService service;

    // not used
    @Override
    public ResponseEntity<?> restApi(GetChatUnreadRequest request) throws BusinessException {
        return null;
    }

    /**
     * get the count of unread messages
     * 
     * @param request contains the unread message count
     */
    @PostMapping(RestApiEndPoints.CHAT_READ_STATUS_GET)
    public ResponseEntity<?> getUnreadCount(@RequestBody GetChatUnreadRequest request) throws BusinessException {
        super.preProcessRequest(request);
        GetChatUnreadReseponse out = service.getUnread(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }

    /**
     * set the count of unread messages
     * 
     * @param request contains the unread messages would be set
     */
    @PostMapping(RestApiEndPoints.CHAT_READ_STATUS_SET)
    public ResponseEntity<?> setUnreadCount(@RequestBody SetChatUnreadRequest request) throws BusinessException {
        super.preProcessRequest(request);
        SetChatUnreadResponse out = service.setUnread(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
