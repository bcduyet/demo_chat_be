package co.jp.xeex.chat.domains.chatmngr.group.save;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

/**
 * SaveGroupRequest
 * 
 * @author q_thinh
 */
public class SaveGroupRequest extends RequestBase {
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String groupId = StringUtils.EMPTY;

    // Group name
    @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 80, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String groupName;

    // List group member
    @Nullable
    public List<String> members = new ArrayList<>();
}
