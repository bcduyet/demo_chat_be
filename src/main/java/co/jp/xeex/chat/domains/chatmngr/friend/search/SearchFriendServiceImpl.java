package co.jp.xeex.chat.domains.chatmngr.friend.search;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatFriendRepository;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * SearchFriendServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class SearchFriendServiceImpl extends ServiceBaseImpl<SearchFriendRequest, SearchFriendResponse>
        implements SearchFriendService {

    // repository uses
    private final ChatFriendRepository friendsRepo;

    public SearchFriendServiceImpl(ChatFriendRepository friendsRepo) {
        this.friendsRepo = friendsRepo;
    }

    @Override
    public SearchFriendResponse processRequest(SearchFriendRequest in) throws BusinessException {
        // Set searchValue
        in.setSearchValue((in.getSearchValue() == null || AppConstant.STAR_CHARACTER.equals(in.getSearchValue()))
                ? StringUtils.EMPTY
                : in.getSearchValue());

        // Get list friend
        List<FriendDto> friendDtos = friendsRepo.findByValue(in.requestBy, in.getSearchValue());

        // Response
        SearchFriendResponse response = new SearchFriendResponse();
        response.setFriends(friendDtos);
        return response;
    }
}
