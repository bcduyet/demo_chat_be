package co.jp.xeex.chat.domains.chatmngr.friend.getnone;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import lombok.Data;

/**
 * GetNoneFriendMemberResponse
 * 
 * @author q_thinh
 */
@Data
public class GetNoneFriendMemberResponse {
    private List<MemberDto> members;
}
