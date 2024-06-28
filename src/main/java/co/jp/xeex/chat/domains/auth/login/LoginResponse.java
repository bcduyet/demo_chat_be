package co.jp.xeex.chat.domains.auth.login;

import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String userName;
    private String email;
    private String fullName;
    /**
     * Get/set role code list
     */
    private String roleIdList;
    private String lang;
    // ...
}
