package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DfwM030M
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "dfw_m030m")
public class Department extends EntityBase {
    @Column(name = "company_cd", length = 10, nullable = false, unique = true)
    private String companyCd;

    @Column(name = "dept_name", length = 100, nullable = false, unique = true)
    private String deptName;

    @Column(name = "dept_f", columnDefinition = "tinyint(1) default 0")
    private boolean deptF;
}
