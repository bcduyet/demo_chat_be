package co.jp.xeex.chat.domains.auth.refeshtoken;

import co.jp.xeex.chat.base.RequestBase;
import jakarta.validation.constraints.NotBlank;

/**
 * @author v_long
 */
public class RefreshTokenRequest extends RequestBase{
    /**
     * The refresh token use to create new tokens
     */
    @NotBlank(message = "VALIDATE_ERR_EMPTY")
    public String refreshToken;
}
