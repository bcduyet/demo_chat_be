package co.jp.xeex.chat.domains.chatmngr.dept.search;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.dept.dto.DeptDetailDto;
import lombok.Data;

/**
 * SearchDeptResponse
 * 
 * @author q_thinh
 */
@Data
public class SearchDeptResponse {
    private List<DeptDetailDto> depts;
}
