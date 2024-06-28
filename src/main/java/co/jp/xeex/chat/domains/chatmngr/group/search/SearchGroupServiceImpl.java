package co.jp.xeex.chat.domains.chatmngr.group.search;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDetailDto;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * SearchGroupServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class SearchGroupServiceImpl extends ServiceBaseImpl<SearchGroupRequest, SearchGroupResponse>
        implements SearchGroupService {

    // repository uses
    private final ChatGroupRepository chatGroupRepo;
    private final ChatMessageRepository chatMessageRepo;

    public SearchGroupServiceImpl(ChatGroupRepository chatGroupRepo,
            ChatMessageRepository chatMessageRepo) {
        this.chatGroupRepo = chatGroupRepo;
        this.chatMessageRepo = chatMessageRepo;
    }

    @Override
    public SearchGroupResponse processRequest(SearchGroupRequest in) throws BusinessException {
        // Set searchValue
        in.setSearchValue((in.getSearchValue() == null || AppConstant.STAR_CHARACTER.equals(in.getSearchValue()))
                ? StringUtils.EMPTY
                : in.getSearchValue());

        // Search group
        List<ChatGroupDetailDto> chatGroupDetailDtos = getChatGroupDetails(in.requestBy, in.getSearchValue());

        // Response
        SearchGroupResponse response = new SearchGroupResponse();
        response.setGroups(chatGroupDetailDtos);
        return response;
    }

    /**
     * getChatGroupDetails
     * 
     * @param empCd
     * @param searchValue
     * @return
     */
    private List<ChatGroupDetailDto> getChatGroupDetails(String empCd, String searchValue) {
        List<ChatGroupDetailDto> result = new ArrayList<>();

        // Get all chatgroup info
        List<ChatGroupDto> chatGroupDtos = chatGroupRepo.findGroupByValue(empCd, searchValue);
        for (ChatGroupDto chatGroupDto : chatGroupDtos) {
            ChatGroupDetailDto chatGroupDetailDto = new ChatGroupDetailDto();
            chatGroupDetailDto.setGroupId(chatGroupDto.getGroupId());
            chatGroupDetailDto.setGroupName(chatGroupDto.getGroupName());
            chatGroupDetailDto.setStartMessageId(chatMessageRepo.getStartMessageIdByGroup(chatGroupDto.getGroupId()));
            result.add(chatGroupDetailDto);
        }

        return result;
    }
}
