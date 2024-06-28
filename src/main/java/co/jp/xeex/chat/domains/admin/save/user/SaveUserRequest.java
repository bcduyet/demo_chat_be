package co.jp.xeex.chat.domains.admin.save.user;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

public class SaveUserRequest extends RequestBase{
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    @Pattern(regexp = DtoValidateConsts.PATTERN_HW_NO_SPACE, message = DtoValidateConsts.VALIDATE_ERR_HW_NO_SPACE)    
    public String userName;

    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 10, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String deptCd;

    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Email(message = DtoValidateConsts.VALIDATE_ERR_EMAIL)
    public String email;

    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Pattern(regexp = DtoValidateConsts.PATTERN_HW_NO_SPACE, message = DtoValidateConsts.VALIDATE_ERR_HW_NO_SPACE)
    @Pattern(regexp = DtoValidateConsts.PATTERN_HW_PASSWORD, message = DtoValidateConsts.VALIDATE_ERR_HW_PASSWORD)
    public String password;

    @Length(max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MAX)
    public String fullName;

    @Min(value = 0, message = DtoValidateConsts.VALIDATE_ERR_VAL_MIN)
    @Max(value = 9, message = DtoValidateConsts.VALIDATE_ERR_VAL_MAX)
    public int activeFlag;
}
