package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "unread_message")
public class UnreadMessage extends EntityBase {
    @Column(name = "chat_group_id", nullable = false, columnDefinition = "varchar(36)")
    private String chatGroupId;

    @Column(name = "emp_cd", nullable = false, columnDefinition = "varchar(36)")
    private String userId;

    @Column(name = "unread_count", columnDefinition = "integer default 0")
    private int unreadCount;
}
