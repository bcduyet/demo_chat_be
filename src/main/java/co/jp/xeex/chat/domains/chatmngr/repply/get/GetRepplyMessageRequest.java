package co.jp.xeex.chat.domains.chatmngr.repply.get;

import org.apache.commons.lang3.StringUtils;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * GetRepplyMessageRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetRepplyMessageRequest extends RequestBase {
    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    private String repplyMessageId;

    @Min(value = 1, message = DtoValidateConsts.VALIDATE_ERR_VAL_MIN)
    @Max(value = 100, message = DtoValidateConsts.VALIDATE_ERR_VAL_MAX)
    private Integer limitMessage = 20;

    // Use to get messages older messageId
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    private String messageId = StringUtils.EMPTY;
}
