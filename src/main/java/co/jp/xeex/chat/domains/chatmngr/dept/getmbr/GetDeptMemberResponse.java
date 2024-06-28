package co.jp.xeex.chat.domains.chatmngr.dept.getmbr;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import lombok.Data;

/**
 * GetDeptMemberResponse
 * 
 * @author q_thinh
 */
@Data
public class GetDeptMemberResponse {
    private List<MemberDto> members;
}
