package co.jp.xeex.chat.domains.chatmngr.thread.get;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * GetThreadMessageRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetThreadMessageRequest extends RequestBase {
    @Min(value = 1, message = DtoValidateConsts.VALIDATE_ERR_VAL_MIN)
    @Max(value = 100, message = DtoValidateConsts.VALIDATE_ERR_VAL_MAX)
    private Integer limitMessage = 20;
}
