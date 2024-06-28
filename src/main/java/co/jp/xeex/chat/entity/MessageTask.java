package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * MessageTask
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity

@Table(name = "message_task")
public class MessageTask extends EntityBase {
    @Column(name = "task_id", nullable = false, unique = true, columnDefinition = "varchar(36)")
    private String taskId;

    @Column(name = "message_id", nullable = true, unique = true, columnDefinition = "varchar(36)")
    private String messageId;
}
