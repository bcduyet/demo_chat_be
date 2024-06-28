package co.jp.xeex.chat.domains.chatmngr.thread.get;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.thread.dto.ThreadMessageDto;
import lombok.Data;

/**
 * GetThreadMessageResponse
 * 
 * @author q_thinh
 */
@Data
public class GetThreadMessageResponse {
    List<ThreadMessageDto> threadMessage;
}
