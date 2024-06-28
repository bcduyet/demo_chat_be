package co.jp.xeex.chat.domains.admin.dto;

import lombok.Data;

/**
 * Represents a user data transfer object (DTO).
 * This class contains the properties of a user.
 */
@Data
public class UserDto {
    private String id;
    private String userName;
    private String deptCd;
    private String fullName;
    private String email;
    private Integer activeFlag;
    private Integer useFlag;
}
