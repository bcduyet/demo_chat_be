package co.jp.xeex.chat.domains.chatmngr.group.save;

import java.util.List;

import lombok.Data;

/**
 * SaveGroupResponse
 * 
 * @author q_thinh
 */
@Data
public class SaveGroupResponse {
    private String groupId;
    private String groupName;
    private String keyMember;
    private List<String> members;
}
