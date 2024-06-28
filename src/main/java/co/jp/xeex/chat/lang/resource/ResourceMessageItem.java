package co.jp.xeex.chat.lang.resource;

import lombok.Data;

@Data
public class ResourceMessageItem {
    private String message;
    private String messageId;
    private String sourceName;
    private String lang;
    /**
     * The type of the message (SYSTEM, BUSINESS, VALIDATION,...)
     */
    private ResourceMessageTypes type;
    /**
     * The level of the message (INFO, WARNING, ERROR)
     */
    private ResourceMessageLevels level;

    @Override
    public String toString() {
        return String.format("MessageItem: {message: %s, messageId: %s, sourceName: %s, lang: %s, type: %s, level: %s}",
                message, messageId, sourceName, lang, type, level);
    }
}
