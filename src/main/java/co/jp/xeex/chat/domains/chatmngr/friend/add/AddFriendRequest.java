package co.jp.xeex.chat.domains.chatmngr.friend.add;

import java.util.List;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotEmpty;

/**
 * AddFriendRequest
 * 
 * @author q_thinh
 */
public class AddFriendRequest extends RequestBase {
    // Friend username
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    public List<String> empCdFriends;
}
