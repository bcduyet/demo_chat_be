package co.jp.xeex.chat.domains.chatmngr.dept.dto;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import lombok.Data;

/**
 * DeptDetailDto
 * 
 * @author q_thinh
 */
@Data
public class DeptDetailDto {
    private String deptCd;
    private String deptName;
    /**
     * List member in department
     */
    private List<MemberDto> members;
}
