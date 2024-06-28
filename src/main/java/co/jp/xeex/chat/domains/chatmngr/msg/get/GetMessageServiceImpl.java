package co.jp.xeex.chat.domains.chatmngr.msg.get;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.service.ChatMessageService;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.entity.ChatGroup;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatGroupRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * GetMessageServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class GetMessageServiceImpl extends ServiceBaseImpl<GetMessageRequest, GetMessageResponse>
        implements GetMessageService {

    // Error keys
    private static final String GET_MESSAGE_ERR_CHAT_GROUP_IS_NOT_EXISTED = "GET_MESSAGE_ERR_CHAT_GROUP_IS_NOT_EXISTED";
    private static final String GET_MESSAGE_ERR_PERMISSION_DENIED = "GET_MESSAGE_ERR_PERMISSION_DENIED";

    // DI
    private ChatMessageRepository chatMessageRepo;
    private ChatGroupRepository chatGroupRepo;
    private ChatMessageService chatMessageService;

    @Override
    public GetMessageResponse processRequest(GetMessageRequest in) throws BusinessException {
        // Validation chat group
        ChatGroup chatGroup = chatGroupRepo.findById(in.getGroupId()).orElse(null);
        if (chatGroup == null) {
            throw new BusinessException(GET_MESSAGE_ERR_CHAT_GROUP_IS_NOT_EXISTED, in.lang);
        }

        // Check permission user
        List<String> groupMember = chatGroupRepo.getUserByGroupId(in.getGroupId());
        if (!groupMember.contains(in.requestBy)) {
            throw new BusinessException(GET_MESSAGE_ERR_PERMISSION_DENIED, in.lang);
        }

        // Get messages
        List<ChatMessageDetailDto> chatMessageDetails;
        Optional<ChatMessage> chatMessageOpt = chatMessageRepo.findById(in.getMessageId());
        if (chatMessageOpt.isPresent()) {
            // Get message for messageId
            ChatMessage chatMessage = chatMessageOpt.get();
            chatMessageDetails = chatMessageRepo.findByValue(in.getGroupId(), chatMessage.getCreateAt(),
                    in.getLimitMessage());
        } else {
            // Get latest chat messages
            chatMessageDetails = chatMessageRepo.findByGroup(in.getGroupId(), in.getLimitMessage());
        }

        // Reverse messages (ASC)
        Collections.reverse(chatMessageDetails);

        // Get messages
        List<ChatMessageDto> messages = getChatMessages(chatMessageDetails);

        // Response
        GetMessageResponse response = new GetMessageResponse();
        response.setGroupId(chatGroup.getId());
        response.setGroupName(chatGroup.getGroupName());
        response.setMessage(messages);
        return response;
    }

    /**
     * getChatMessages
     * 
     * @param chatMessageDetails
     * @return
     */
    private List<ChatMessageDto> getChatMessages(List<ChatMessageDetailDto> chatMessageDetails) {
        List<ChatMessageDto> result = new ArrayList<>();
        if (chatMessageDetails.isEmpty()) {
            return result;
        }

        // Add chatMsgDto
        for (ChatMessageDetailDto chatMessageDetail : chatMessageDetails) {
            result.add(chatMessageService.getChatMessageDtoByDetailObj(chatMessageDetail));
        }

        return result;
    }
}
