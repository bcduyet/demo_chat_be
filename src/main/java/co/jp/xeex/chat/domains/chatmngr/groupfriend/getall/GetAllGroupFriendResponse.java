package co.jp.xeex.chat.domains.chatmngr.groupfriend.getall;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDetailDto;
import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDetailDto;
import lombok.Data;

/**
 * GetAllGroupFriendResponse
 * 
 * @author q_thinh
 */
@Data
public class GetAllGroupFriendResponse {
    /**
     * List chat group detail
     * Client use when begin load
     */
    private List<ChatGroupDetailDto> groups;
    /**
     * List friend detail
     * Client use when begin load
     */
    private List<FriendDetailDto> friends;
}
