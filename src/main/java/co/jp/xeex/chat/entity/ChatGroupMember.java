package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * ChatGroupMember
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "chat_group_member")
public class ChatGroupMember extends EntityBase {
    @Column(name = "group_id", nullable = false, columnDefinition = "varchar(36)")
    private String groupId;

    @Column(name = "member_emp_cd", length = 45, nullable = false)
    private String memberEmpCd;
}
