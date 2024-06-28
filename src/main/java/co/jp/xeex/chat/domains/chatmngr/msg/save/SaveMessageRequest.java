package co.jp.xeex.chat.domains.chatmngr.msg.save;

import org.apache.commons.lang3.StringUtils;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * SaveMessageRequest
 * 
 * @author q_thinh
 */
public class SaveMessageRequest extends RequestBase {
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String messageId = StringUtils.EMPTY;

    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String repplyMessageId = StringUtils.EMPTY;

    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String groupId;

    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    public String chatContent;
}
