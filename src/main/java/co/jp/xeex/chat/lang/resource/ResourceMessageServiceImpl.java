package co.jp.xeex.chat.lang.resource;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The service to get the message from the message resource.
 * (support the multi-language message)
 * implementation ResourceMessageService
 */
@Service
public class ResourceMessageServiceImpl extends ServiceBaseImpl<ResourceMessageRequestDto, ResourceMessageResponseDto>
        implements ResourceMessageService {

    private final MessageResourceConfig messageResourceConfig;

    public ResourceMessageServiceImpl(MessageResourceConfig messageResourceConfig) {
        this.messageResourceConfig = messageResourceConfig;
    }

    @Override
    protected ResourceMessageResponseDto processRequest(ResourceMessageRequestDto in) throws BusinessException {
        ResourceMessageResponseDto out = new ResourceMessageResponseDto();
        Map<String, List<ResourceMessageItem>> messageResources = messageResourceConfig.allMessageResourcesMap();
        if (in.allLang) {
            out.setItems(messageResources);
            out.setCount(messageResourceConfig.count());
        } else {// allLang = false
            Map<String, List<ResourceMessageItem>> oneLangMap = new HashMap<>();
            List<ResourceMessageItem> items = messageResources.get(in.lang);
            oneLangMap.put(in.lang, items);
            out.setCount(items.size());
            out.setItems(oneLangMap);
        }
        return out;
    }

    @Override
    public String getMessage(String msgId, String lang) {
        return this.messageResourceConfig.allMessageResourcesList().stream()
                .filter(item -> item.getMessageId().equals(msgId) && item.getLang().equals(lang))
                .findFirst().map(ResourceMessageItem::getMessage).orElse(msgId);
    }

}
