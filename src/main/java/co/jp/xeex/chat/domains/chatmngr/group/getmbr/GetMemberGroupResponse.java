package co.jp.xeex.chat.domains.chatmngr.group.getmbr;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import lombok.Data;

/**
 * GetMemberGroupResponse
 * 
 * @author q_thinh
 */
@Data
public class GetMemberGroupResponse {
    private List<MemberDto> groupMembers;
}
