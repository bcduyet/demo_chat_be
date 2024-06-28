package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TaskFile
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "task_file")
public class TaskFile extends EntityBase {
    @Column(name = "task_id", nullable = false, columnDefinition = "varchar(36)")
    private String taskId;

    @Column(name = "file_id", nullable = false, columnDefinition = "varchar(36)")
    private String fileId;
}
