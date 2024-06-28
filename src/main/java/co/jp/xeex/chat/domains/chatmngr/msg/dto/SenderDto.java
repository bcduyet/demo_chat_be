package co.jp.xeex.chat.domains.chatmngr.msg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SenderDto
 * 
 * @author q_thinh
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderDto {
    private String sender;
    private String senderImage;
    private String fullName;
}
