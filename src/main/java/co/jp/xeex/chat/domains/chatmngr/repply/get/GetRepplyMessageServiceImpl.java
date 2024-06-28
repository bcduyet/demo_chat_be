package co.jp.xeex.chat.domains.chatmngr.repply.get;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.RepplyMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.service.ChatMessageService;
import co.jp.xeex.chat.domains.chatmngr.repply.mapper.ChatMessageMapper;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * GetRepplyMessageServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class GetRepplyMessageServiceImpl
        extends ServiceBaseImpl<GetRepplyMessageRequest, GetRepplyMessageResponse>
        implements GetRepplyMessageService {

    // DI
    private ChatMessageRepository chatMessageRepo;
    private ChatMessageMapper chatMessageMapper;
    private ChatMessageService chatMessageService;

    @Override
    public GetRepplyMessageResponse processRequest(GetRepplyMessageRequest in) throws BusinessException {
        // Get messages
        List<ChatMessageDetailDto> chatMessageDetails;
        Optional<ChatMessage> chatMessageOpt = chatMessageRepo.findById(in.getMessageId());
        if (chatMessageOpt.isPresent()) {
            // Get repply message for messageId
            ChatMessage chatMessage = chatMessageOpt.get();
            chatMessageDetails = chatMessageRepo.findRepplyMessageByValue(in.getRepplyMessageId(),
                    chatMessage.getCreateAt(),
                    in.getLimitMessage());
        } else {
            // Get latest repply chat message
            chatMessageDetails = chatMessageRepo.findRepplyMessageById(in.getRepplyMessageId(), in.getLimitMessage());
        }

        // Reverse messages (ASC)
        Collections.reverse(chatMessageDetails);

        // main repply message info
        ChatMessageDetailDto mainRepplyMessageDetail = chatMessageRepo.findMessageDetailById(in.getRepplyMessageId());
        if (mainRepplyMessageDetail == null) {
            return new GetRepplyMessageResponse();
        }

        // Setting repply message
        ChatMessageDto mainChatMsgDto = chatMessageService.getChatMessageDtoByDetailObj(mainRepplyMessageDetail);
        RepplyMessageDetailDto repplyMessageDetailDto = mainChatMsgDto.repplyMessage;
        if (repplyMessageDetailDto != null) {
            repplyMessageDetailDto.setMessage(getRepplyMessages(chatMessageDetails));
            mainChatMsgDto.repplyMessage = repplyMessageDetailDto;
        }

        // Response
        GetRepplyMessageResponse response = new GetRepplyMessageResponse();
        response.setMessage(mainChatMsgDto);
        return response;
    }

    /**
     * getRepplyMessages
     * 
     * @param chatMessageDetails
     * @return
     */
    private List<ChatMessageDto> getRepplyMessages(List<ChatMessageDetailDto> chatMessageDetails) {
        List<ChatMessageDto> resultMessages = new ArrayList<>();

        // Add repply message
        for (ChatMessageDetailDto chatMessageDetail : chatMessageDetails) {
            ChatMessageDto chatMessageDto = chatMessageMapper.chatMessageDetailToDto(chatMessageDetail);
            chatMessageDto.chatFiles = chatMessageService.getChatFileDto(chatMessageDetail.getMessageId());
            resultMessages.add(chatMessageDto);
        }
        return resultMessages;
    }
}
