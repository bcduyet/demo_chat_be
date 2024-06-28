package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatFriend
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity

@Table(name = "chat_friend")
public class ChatFriend extends EntityBase {
    @Column(name = "group_id", nullable = false, columnDefinition = "varchar(36)")
    private String groupId;

    @Column(name = "emp_cd_1", length = 45, nullable = false)
    private String empCd1;

    @Column(name = "emp_cd_2", length = 45, nullable = false)
    private String empCd2;
}
