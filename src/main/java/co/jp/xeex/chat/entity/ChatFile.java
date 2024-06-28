package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatFile
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "chat_file")
public class ChatFile extends EntityBase {
    @Column(name = "message_id", nullable = false, columnDefinition = "varchar(36)")
    private String messageId;

    @Column(name = "file_id", nullable = false, columnDefinition = "varchar(36)")
    private String fileId;
}
