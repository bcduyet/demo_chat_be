package co.jp.xeex.chat.domains.chatmngr.msg.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatAction;
import co.jp.xeex.chat.domains.chat.ChatMessageDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.RepplyMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.SenderDto;
import co.jp.xeex.chat.domains.chatmngr.repply.mapper.ChatMessageMapper;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.domains.file.enums.FileType;
import co.jp.xeex.chat.entity.ChatFile;
import co.jp.xeex.chat.entity.ChatMessage;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.lang.resource.ResourceMessageService;
import co.jp.xeex.chat.repository.ChatFileRepository;
import co.jp.xeex.chat.repository.ChatMessageRepository;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.MessageTaskRepository;
import co.jp.xeex.chat.util.DateTimeUtil;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

/**
 * ChatMessageServiceImpl
 * 
 * @author q_thinh
 */
@AllArgsConstructor
@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    // Info key
    private static final String DELETE_MESSAGE_INFO_CHAT_CONTENT = "DELETE_MESSAGE_INFO_CHAT_CONTENT";

    // DI
    private ChatMessageRepository chatMessageRepo;
    private FileRepository fileRepo;
    private ChatFileRepository chatFileRepo;
    private MessageTaskRepository messageTaskRepo;
    private EnvironmentUtil environmentUtil;
    private ChatMessageMapper chatMessageMapper;
    private ResourceMessageService multiLangMessageService;

    /**
     * get chat message dto by id
     * 
     * @param messageId
     * @return ChatMessageDto
     */
    @Override
    public ChatMessageDto getChatMessageDtoById(String messageId) {
        ChatMessageDetailDto chatMessageDetailDto = chatMessageRepo.findMessageDetailById(messageId);
        return getChatMessageDtoByDetailObj(chatMessageDetailDto);
    }

    /**
     * get chat message dto by detail obj
     * 
     * @param chatMessageDetailDto
     * @return ChatMessageDto
     */
    @Override
    public ChatMessageDto getChatMessageDtoByDetailObj(ChatMessageDetailDto chatMessageDetailDto) {
        ChatMessageDto chatMsgDto = chatMessageMapper.chatMessageDetailToDto(chatMessageDetailDto);

        // Setting task id
        chatMsgDto.taskId = messageTaskRepo.getTaskIdByMessageId(chatMessageDetailDto.getMessageId());

        // Setting avatar
        if (!StringUtils.isEmpty(chatMessageDetailDto.getSenderImage())) {
            chatMsgDto.senderImage = String.format(AppConstant.FILE_URL,
                    environmentUtil.getDomain(), AppConstant.PATH_AVATAR_PREFIX, chatMessageDetailDto.getSenderImage());
        }

        // Add repply message
        chatMsgDto.repplyMessage = getRepplyMessageDetail(chatMessageDetailDto.getMessageId());

        // Add chat files
        chatMsgDto.chatFiles = getChatFileDto(chatMessageDetailDto.getMessageId());

        return chatMsgDto;
    }

    /**
     * getRepplyMessageInfo
     * 
     * @param repplyMessageId
     * @return RepplyMsgInfoDto
     */
    @Override
    public RepplyMessageDetailDto getRepplyMessageDetail(String repplyMessageId) {
        RepplyMessageDetailDto result = new RepplyMessageDetailDto();

        // Add repply message info
        Integer repplyCount = chatMessageRepo.getRepplyCount(repplyMessageId);
        if (repplyCount == 0) {
            return null;
        }
        result.setAllRepply(repplyCount);

        // Add last repply
        Timestamp lastRepply = chatMessageRepo.getLastRepplyMessageById(repplyMessageId);
        result.setLastRepply(DateTimeUtil.getZoneDateTimeString(lastRepply));

        // Add start message repply id
        result.setStartMessageId(chatMessageRepo.getStartMessageByRepplyId(repplyMessageId));

        // Add all repply user info
        String domain = environmentUtil.getDomain();
        List<SenderDto> senderDtos = chatMessageRepo.findRepplyUserById(repplyMessageId);
        for (SenderDto senderDto : senderDtos) {
            if (!StringUtils.isEmpty(senderDto.getSenderImage())) {
                String fileUrl = String.format(AppConstant.FILE_URL,
                        domain, AppConstant.PATH_AVATAR_PREFIX, senderDto.getSenderImage());
                senderDto.setSenderImage(fileUrl);
            }
        }
        result.setAllUserRepply(senderDtos);

        // Add messages
        result.setMessage(new ArrayList<>());
        return result;
    }

    /**
     * save chat files
     * 
     * @param files
     * @param chatMessage
     * @throws IOException
     */
    @Override
    public void saveChatFile(List<FileDto> files, ChatMessage chatMessage) throws BusinessException {
        if (files == null || files.isEmpty()) {
            return;
        }

        // Save chat file
        String domain = environmentUtil.getDomain();
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
        for (FileDto fileDto : files) {
            try {
                FileUtil.saveTempFile(envRootUploadPath,
                        chatMessage.getGroupId(),
                        fileDto.getStoreName(),
                        chatMessage.getCreateBy(),
                        true);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), null);
            }

            // Save file
            File file = new File();
            file.initDefault(chatMessage.getCreateBy());
            file.setOriginName(fileDto.getOriginName());
            file.setStoreName(fileDto.getStoreName());
            file.setFileType(fileDto.getFileType());
            fileRepo.saveAndFlush(file);

            // Save chat file
            ChatFile chatFile = new ChatFile();
            chatFile.initDefault(chatMessage.getCreateBy());
            chatFile.setMessageId(chatMessage.getId());
            chatFile.setFileId(file.getId());
            chatFileRepo.saveAndFlush(chatFile);

            // Set response file url
            String fileUrl = String.format(AppConstant.FILE_URL, domain, AppConstant.PATH_CHAT_PREFIX,
                    fileDto.getStoreName());
            fileDto.setDownloadUrl(fileUrl);
            if (FileType.IMAGE.equals(fileDto.getFileType())) {
                fileDto.setImageUrl(fileUrl);
            }
        }
    }

    /**
     * get chat file dto
     * 
     * @param messageId
     */
    @Override
    public List<FileDto> getChatFileDto(String messageId) {
        List<FileDto> result = new ArrayList<>();
        List<File> files = fileRepo.findByMessageId(messageId);

        for (File file : files) {
            FileDto fileDto = new FileDto();
            fileDto.setOriginName(file.getOriginName());
            fileDto.setStoreName(file.getStoreName());
            fileDto.setFileType(file.getFileType());
            fileDto.setEmpCd(file.getCreateBy());
            fileDto.setDownloadUrl(String.format(AppConstant.FILE_URL, environmentUtil.getDomain(),
                    AppConstant.PATH_CHAT_PREFIX, file.getStoreName()));
            if (FileType.IMAGE.equals(file.getFileType())) {
                fileDto.setImageUrl(fileDto.getDownloadUrl());
            }
            result.add(fileDto);
        }

        return result;
    }

    /**
     * delete or edit chat message
     * 
     * @param chatMessage
     * @param lang
     */
    @Override
    public void deleteOrEditChatMessage(ChatMessage chatMessage, String lang) {
        if (chatMessage != null) {
            int repplyCount = chatMessageRepo.getRepplyCount(chatMessage.getId());
            if (repplyCount > 0) {
                // Update delete message
                chatMessage.setChatContent(String.format(
                        multiLangMessageService.getMessage(DELETE_MESSAGE_INFO_CHAT_CONTENT, lang)));
                chatMessage.setAction(ChatAction.EDIT_DELETE_CHAT);
                chatMessageRepo.saveAndFlush(chatMessage);
            } else {
                // Delete message
                chatMessage.setAction(ChatAction.DELETE_CHAT);
                chatMessageRepo.delete(chatMessage);
            }
        }
    }
}
