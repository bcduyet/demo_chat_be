package co.jp.xeex.chat.domains.chatmngr.friend.search;

import co.jp.xeex.chat.base.RequestBase;
import io.micrometer.common.lang.Nullable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SearchFriendRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchFriendRequest extends RequestBase {
    // Search friend value
    @Nullable
    private String searchValue;
}
