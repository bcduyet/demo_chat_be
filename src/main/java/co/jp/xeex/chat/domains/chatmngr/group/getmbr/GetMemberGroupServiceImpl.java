package co.jp.xeex.chat.domains.chatmngr.group.getmbr;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * GetMemberGroupServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class GetMemberGroupServiceImpl extends ServiceBaseImpl<GetMemberGroupRequest, GetMemberGroupResponse>
        implements GetMemberGroupService {

    // repository uses
    private final UserRepository userRepo;

    public GetMemberGroupServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public GetMemberGroupResponse processRequest(GetMemberGroupRequest in) throws BusinessException {
        // Search member
        List<MemberDto> memberGroups = userRepo.findByGroup(in.getGroupId());

        // Response
        GetMemberGroupResponse response = new GetMemberGroupResponse();
        response.setGroupMembers(memberGroups);
        return response;
    }
}
