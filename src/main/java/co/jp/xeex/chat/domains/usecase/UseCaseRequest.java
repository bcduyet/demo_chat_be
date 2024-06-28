package co.jp.xeex.chat.domains.usecase;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DateConstraint;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UseCaseRequest extends RequestBase {
    /**
     * The login username.
     */
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String field1;
    /**
     * The login password.
     */
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 50, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String field2;

    @DateConstraint(format = "yyyyMMdd" ,message = DtoValidateConsts.VALIDATE_ERR_DATE_FORMAT)
    public String birthDate;

}
