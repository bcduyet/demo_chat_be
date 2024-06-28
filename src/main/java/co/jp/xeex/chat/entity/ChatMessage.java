package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import co.jp.xeex.chat.domains.chat.ChatAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatMessage
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "chat_message")
public class ChatMessage extends EntityBase {
    @Column(name = "group_id", nullable = false, columnDefinition = "varchar(36)")
    private String groupId;

    @Column(name = "repply_message_id", columnDefinition = "varchar(36)")
    private String repplyMessageId;

    @Column(name = "chat_content", columnDefinition = "TEXT")
    private String chatContent;

    /**
     * the message action type
     * (LOGIN, LOGOUT, CHAT, JOIN, LEAVE, TYPING, REPPLY, REACTION,...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "chat_action", length = 10, nullable = false)
    private ChatAction action;
}
