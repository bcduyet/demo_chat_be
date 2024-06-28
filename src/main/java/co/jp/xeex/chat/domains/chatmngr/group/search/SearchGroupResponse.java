package co.jp.xeex.chat.domains.chatmngr.group.search;

import java.util.List;

import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDetailDto;
import lombok.Data;

/**
 * SearchGroupResponse
 * 
 * @author q_thinh
 */
@Data
public class SearchGroupResponse {
    private List<ChatGroupDetailDto> groups;
}
