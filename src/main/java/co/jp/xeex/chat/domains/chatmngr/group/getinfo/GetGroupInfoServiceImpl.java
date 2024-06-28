package co.jp.xeex.chat.domains.chatmngr.group.getinfo;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDetailDto;
import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * GetGroupInfoServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class GetGroupInfoServiceImpl extends ServiceBaseImpl<GetGroupInfoRequest, GetGroupInfoResponse>
        implements GetGroupInfoService {

    // Error keys
    private static final String GET_GROUP_INFO_ERR_GROUP_IS_NOT_EXISTED = "GET_GROUP_INFO_ERR_GROUP_IS_NOT_EXISTED";

    // DI
    private final ChatGroupRepository chatGroupRepo;
    private final UserRepository userRepo;

    public GetGroupInfoServiceImpl(ChatGroupRepository chatGroupRepo,
            UserRepository userRepo) {
        this.chatGroupRepo = chatGroupRepo;
        this.userRepo = userRepo;
    }

    @Override
    public GetGroupInfoResponse processRequest(GetGroupInfoRequest in) throws BusinessException {
        // Get chatGroup
        ChatGroup chatGroup = chatGroupRepo.findById(in.groupId).orElse(null);
        if (chatGroup == null) {
            throw new BusinessException(GET_GROUP_INFO_ERR_GROUP_IS_NOT_EXISTED, in.lang);
        }

        // Get members
        List<MemberDto> memberChatGroups = userRepo.findByGroup(in.groupId);

        // Setting ChatGroup info
        ChatGroupDetailDto chatGroupDetailDto = new ChatGroupDetailDto();
        chatGroupDetailDto.setGroupId(chatGroup.getId());
        chatGroupDetailDto.setGroupName(chatGroup.getGroupName());
        chatGroupDetailDto.setMembers(memberChatGroups);

        // Response
        GetGroupInfoResponse response = new GetGroupInfoResponse();
        response.setGroupInfo(chatGroupDetailDto);
        return response;
    }
}
