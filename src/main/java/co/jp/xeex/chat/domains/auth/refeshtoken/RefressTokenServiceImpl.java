package co.jp.xeex.chat.domains.auth.refeshtoken;

import org.springframework.stereotype.Service;

import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.token.JwtTokenService;
import co.jp.xeex.chat.token.TokenClaimData;
import co.jp.xeex.chat.token.TokenType;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefressTokenServiceImpl implements RefressTokenService {
    private static final String LOGIN_ERR_USER_NOT_EXITED = "LOGIN_ERR_USER_NOT_EXITED";
    private static final String REFRESH_TOKEN_ERR_REFRESH_TOKEN_INVALID = "REFRESH_TOKEN_ERR_REFRESH_TOKEN_INVALID";
    private JwtTokenService jwtTokenService;
    private UserRepository userRepository;

    @Override
    public RefreshTokenResponse execute(RefreshTokenRequest request) throws BusinessException{
        if (jwtTokenService.validateToken(request.refreshToken)) {
            TokenClaimData claim = jwtTokenService.getClaimData(request.refreshToken);// throw new bus exception here
            User user = userRepository.findByUserName(claim.getUserName());
            if (user == null) {
                throw new BusinessException(LOGIN_ERR_USER_NOT_EXITED, request.lang);
            }
            RefreshTokenResponse dto = new RefreshTokenResponse();
            // create new refresh token
            String newRefreshToken = jwtTokenService.createToken(new TokenClaimData() {
                @Override
                public String getUserId() {
                    return claim.getUserId();
                }

                @Override
                public String getUserName() {
                    return claim.getUserName();
                }

                @Override
                public String getFullName() {
                    return claim.getFullName();
                }

                @Override
                public String getEmail() {
                    return claim.getEmail();
                }

                @Override
                public String getLang() {
                    return claim.getLang();
                }

                @Override
                public TokenType getTokenType() {
                    return TokenType.REFRESH;
                }
            });
            dto.setRefreshToken(newRefreshToken);
            //
            // create new access token
            String newAccessToken = jwtTokenService.createToken(new TokenClaimData() {
                @Override
                public String getUserId() {
                    return claim.getUserId();
                }

                @Override
                public String getUserName() {
                    return claim.getUserName();
                }

                @Override
                public String getFullName() {
                    return claim.getFullName();
                }

                @Override
                public String getEmail() {
                    return claim.getEmail();
                }

                @Override
                public String getLang() {
                    return claim.getLang();
                }

                @Override
                public TokenType getTokenType() {
                    return TokenType.ACCESS;
                }

            });
            dto.setAccessToken(newAccessToken);
            return dto;
        } else {
            throw new BusinessException(REFRESH_TOKEN_ERR_REFRESH_TOKEN_INVALID, "en");
        }
    }

}
