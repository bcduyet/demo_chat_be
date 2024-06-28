package co.jp.xeex.chat.base;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * The base entit
 * Recommended to be used as a base entity for all entities.
 * 
 * @author v_long, q_thinh
 */
@Data
@MappedSuperclass
public class EntityBase implements Serializable {
    // constants
    public static final Integer ENTITY_IN_USE = 0;
    public static final Integer ENTITY_IN_ARCHIVE = 1;
    public static final Integer ENTITY_DELETED = 2;
    public static final Integer ENTITY_LOCKED = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false, columnDefinition = "varchar(36)")
    private String id;

    @Column(name = "createby", columnDefinition = "varchar(50) default 'system'")
    private String createBy;
    @CreationTimestamp
    @Column(name = "createat", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private Timestamp createAt;
    @Column(name = "updateby", columnDefinition = "varchar(50) default 'system'")
    private String updateBy;
    @UpdateTimestamp
    @Column(name = "updateat", columnDefinition = "TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6)")
    private Timestamp updateAt;
    /**
     * The flag field is used to indicate if the data record is use or not.
     * 0: record is in use
     * 1: record is in achive
     * 2: record is deleted
     * 3: record is locked
     */
    @Column(name = "useFlag", columnDefinition = "smallint default 0") // "smallint default 0
    private Integer useFlag;

    /*
     * 
     * Initialize the default values for the entity.<br>
     * (setting default: createBy, updateBy, useFlag)
     * 
     * @param byUser of system
     */
    public void initDefault(String byUser) {
        this.createBy = byUser;
        this.updateBy = byUser;
        this.useFlag = ENTITY_IN_USE;
    }
}
