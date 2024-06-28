package co.jp.xeex.chat.domains.chatmngr.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChatMemberGroupDto
 * 
 * @author q_thinh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private String memberName;
    private String memberImage;
    private String fullName;
}
