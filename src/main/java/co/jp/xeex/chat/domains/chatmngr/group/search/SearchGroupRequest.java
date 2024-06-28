package co.jp.xeex.chat.domains.chatmngr.group.search;

import co.jp.xeex.chat.base.RequestBase;
import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SearchGroupRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchGroupRequest extends RequestBase {
    // Search group name value
    @Nullable
    private String searchValue;
}
