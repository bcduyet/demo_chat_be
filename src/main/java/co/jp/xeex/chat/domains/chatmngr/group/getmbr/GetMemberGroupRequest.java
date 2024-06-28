package co.jp.xeex.chat.domains.chatmngr.group.getmbr;

import org.apache.commons.lang3.StringUtils;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * GetMemberGroupRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetMemberGroupRequest extends RequestBase {
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    private String groupId = StringUtils.EMPTY;
}
