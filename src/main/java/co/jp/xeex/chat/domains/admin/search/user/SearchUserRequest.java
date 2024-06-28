package co.jp.xeex.chat.domains.admin.search.user;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Email;

public class SearchUserRequest extends RequestBase {
    public String id;
    /**
     * is empCd
     */
    public String userName;
    public String deptCd;
    public String fullName;
    @Email(message = DtoValidateConsts.VALIDATE_ERR_EMAIL)
    public String email;
    public Integer activeFlag;
    public Integer useFlag;
}
