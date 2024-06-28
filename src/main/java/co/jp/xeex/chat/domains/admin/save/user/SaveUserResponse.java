package co.jp.xeex.chat.domains.admin.save.user;

import lombok.Data;

@Data
public class SaveUserResponse {
    private String usrId;
    private String userName;
    private String deptCd;
    private String email;
    private String fullName;
    private Integer activeFlag;
}
