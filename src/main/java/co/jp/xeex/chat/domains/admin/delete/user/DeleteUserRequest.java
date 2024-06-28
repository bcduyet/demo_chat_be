package co.jp.xeex.chat.domains.admin.delete.user;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Size;

public class DeleteUserRequest extends RequestBase {
    @Size(min = 1, max = 1000, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String[] userIds;
}
