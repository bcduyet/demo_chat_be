package co.jp.xeex.chat.domains.chatmngr.friend.getnone;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * GetNoneFriendMemberServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class GetNoneFriendMemberServiceImpl
        extends ServiceBaseImpl<GetNoneFriendMemberRequest, GetNoneFriendMemberResponse>
        implements GetNoneFriendMemberService {

    // DI
    private final UserRepository userRepo;

    public GetNoneFriendMemberServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public GetNoneFriendMemberResponse processRequest(GetNoneFriendMemberRequest in) throws BusinessException {
        // Get list none friend
        List<MemberDto> noneFriends = userRepo.findNoneByFriend(in.requestBy);

        // Response
        GetNoneFriendMemberResponse response = new GetNoneFriendMemberResponse();
        response.setMembers(noneFriends);
        return response;
    }
}
