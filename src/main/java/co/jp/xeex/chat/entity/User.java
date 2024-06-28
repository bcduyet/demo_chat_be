package co.jp.xeex.chat.entity;

import co.jp.xeex.chat.base.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

/**
 * DfwM050M
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "dfw_m050m")
public class User extends EntityBase {
    // constants
    public static final Integer USER_NOT_ACTIVE = 0;
    public static final Integer USER_ACTIVE = 1;
    public static final Integer USER_LOCKED = 2;

    @Column(name = "emp_cd", length = 45, nullable = false, unique = true)
    private String empCd;

    @Column(name = "password", length = 1000, nullable = false)
    private String password;

    @Column(name = "dept_cd", length = 10, nullable = false)
    private String deptCd;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "full_name", length = 45)
    private String fullName;

    @Column(name = "avatar", nullable = true, columnDefinition = "varchar(200) default ''")
    private String avatar;

    /**
     * The login status field is used to indicate if the user is logged in or not.
     * 0: Not logged in
     * 1: Logged in
     */
    @Column(name = "login_status", length = 1, columnDefinition = "smallint default 0")
    private Integer loginStatus;

    /**
     * The last login field is used to store the last login time of the user.
     */
    @Column(name = "last_login", nullable = true, columnDefinition = "timestamp default null")
    private Timestamp lastLogin;
    /**
     * The flag field is used to indicate if the user is active or not.
     * 0: Account is Not active
     * 1: Account is Active
     * 2: Account is Lockew
     * Default is 1
     */
    @Column(name = "active_flag", length = 1, nullable = false, columnDefinition = "smallint default 1")
    private Integer activeFlag;
}
