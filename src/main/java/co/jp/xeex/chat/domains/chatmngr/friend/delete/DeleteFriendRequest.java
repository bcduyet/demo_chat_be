package co.jp.xeex.chat.domains.chatmngr.friend.delete;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DeleteFriendRequest
 * 
 * @author q_thinh
 */
public class DeleteFriendRequest extends RequestBase {
    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    public String friendCd;
}
