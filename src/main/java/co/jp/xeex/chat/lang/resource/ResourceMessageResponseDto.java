package co.jp.xeex.chat.lang.resource;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * The response DTO for the message resource.
 * (support the multi-language message)
 * @author v_long
 */
@Data
public class ResourceMessageResponseDto {
    /**
     * Number of messages
     */
    private Integer count;
    /**
     * List of messages (grouped by language)
     */
    private Map<String,List<ResourceMessageItem>> items;
}
