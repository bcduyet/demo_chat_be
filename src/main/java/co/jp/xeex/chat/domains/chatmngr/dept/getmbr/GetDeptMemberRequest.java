package co.jp.xeex.chat.domains.chatmngr.dept.getmbr;

import co.jp.xeex.chat.base.RequestBase;
import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * GetDeptMemberRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GetDeptMemberRequest extends RequestBase {
    @Nullable
    public String deptCd;
}
