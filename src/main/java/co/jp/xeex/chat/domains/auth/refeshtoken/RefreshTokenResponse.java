package co.jp.xeex.chat.domains.auth.refeshtoken;

import lombok.Data;

/**
 * @author v_long 
 */
@Data
public class RefreshTokenResponse {
    /**
     * The new access token
     */
    private String accessToken;
    /**
     * the new refresh token
     */
    private String refreshToken;
}
