package co.jp.xeex.chat.domains.chatmngr.dept.search;

import co.jp.xeex.chat.base.RequestBase;
import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SearchDeptRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchDeptRequest extends RequestBase {
    // Search dept name value
    @Nullable
    private String searchValue;
}
