package co.jp.xeex.chat.entity;

import java.sql.Timestamp;

import co.jp.xeex.chat.base.EntityBase;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskPriority;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Task
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "task")
public class Task extends EntityBase {
    @Column(name = "task_name", nullable = false)
    private String taskName;

    @Column(name = "group_id", nullable = true, columnDefinition = "varchar(36)")
    private String groupId;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private Timestamp startDate;

    @Column(name = "due_date", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private Timestamp dueDate;

    @Column(name = "assignee", length = 45, nullable = true)
    private String assignee;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * the task status
     * (IN_PROGRESS, DONE...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", length = 15, nullable = true)
    private TaskStatus taskStatus;

    /**
     * the task priority
     * (LOW, NORMAL, HIGHT...)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "task_priority", length = 10, nullable = true)
    private TaskPriority taskPriority;
}
