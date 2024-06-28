package co.jp.xeex.chat.domains.chatmngr.friend.add;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDetailDto;
import lombok.Data;

/**
 * AddFriendResponse
 * 
 * @author q_thinh
 */
@Data
public class AddFriendResponse {
    private List<FriendDetailDto> friends;
}
