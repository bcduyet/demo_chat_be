package co.jp.xeex.chat.domains.auth.changepwd;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class ChangePwdRequest extends RequestBase {
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    public String userName;
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String oldPassword;
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)

    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    @Pattern(regexp = DtoValidateConsts.PATTERN_HW_PASSWORD, message = DtoValidateConsts.VALIDATE_ERR_HW_PASSWORD)
    public String newPassword;

    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    @Pattern(regexp = DtoValidateConsts.PATTERN_HW_PASSWORD, message = DtoValidateConsts.VALIDATE_ERR_HW_PASSWORD)
    public String confirmPassword;
}
