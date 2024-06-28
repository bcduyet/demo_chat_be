package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatGroup
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "chat_group")
public class ChatGroup extends EntityBase {
    @Column(name = "group_name", length = 1000, nullable = false, unique = true)
    private String groupName;

    /**
     * The group type field is used to indicate chat group or chat friend.
     * true: chat group
     * false: chat friend
     */
    @Column(name = "group_type", columnDefinition = "tinyint(1) default 1")
    private boolean groupType;
}
