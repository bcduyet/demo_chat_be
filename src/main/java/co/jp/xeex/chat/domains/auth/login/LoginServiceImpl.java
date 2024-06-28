package co.jp.xeex.chat.domains.auth.login;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.chat.ChatService;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.encryption.EncodeService;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.token.JwtTokenService;
import co.jp.xeex.chat.token.TokenClaimData;
import co.jp.xeex.chat.token.TokenType;
import co.jp.xeex.chat.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * LoginServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class LoginServiceImpl extends ServiceBaseImpl<LoginRequest, LoginResponse> implements LoginService {
    // err message id (add into file message_exception_en.properties)
    private static final String LOGIN_ERR_USER_NOT_EXITED = "LOGIN_ERR_USER_NOT_EXITED";
    private static final String LOGIN_ERR_PASSWORD = "LOGIN_ERR_PASSWORD";

    private UserRepository userRepository;
    private EncodeService encoder;
    private JwtTokenService jwtTokenService;
    private ChatService chatService;

    @Transactional
    @Override
    protected LoginResponse processRequest(LoginRequest in) throws BusinessException {
        User user = userRepository.findByUserName(in.login);

        if (user == null) {
            throw new BusinessException(LOGIN_ERR_USER_NOT_EXITED, in.lang);
        }

        if (encoder.matches(in.password, user.getPassword())) {
            LoginResponse dto = null;
            // create token
            dto = new LoginResponse();
            dto.setUserId(user.getId());
            dto.setUserName(user.getEmpCd());
            dto.setEmail(user.getEmail());
            dto.setFullName(user.getFullName());
            dto.setLang(in.lang);

            String accesstToken = jwtTokenService.createToken(new TokenClaimData() {
                @Override
                public String getLang() {
                    return in.lang;
                }

                @Override
                public String getUserName() {
                    return user.getEmpCd();
                }

                @Override
                public String getEmail() {
                    return user.getEmail();
                }

                @Override
                public String getFullName() {
                    return user.getFullName();
                }

                @Override
                public String getUserId() {
                    return user.getId();
                }

                @Override
                public TokenType getTokenType() {
                    return TokenType.ACCESS;
                }
            });
            //
            dto.setAccessToken(accesstToken);

            String refreshToken = jwtTokenService.createToken(new TokenClaimData() {
                @Override
                public String getLang() {
                    return in.lang;
                }

                @Override
                public String getUserName() {
                    return user.getEmpCd();
                }

                @Override
                public String getEmail() {
                    return user.getEmail();
                }

                @Override
                public String getFullName() {
                    return user.getFullName();
                }

                @Override
                public String getUserId() {
                    return user.getId();
                }

                @Override
                public TokenType getTokenType() {
                    return TokenType.REFRESH;
                }
            });
            dto.setRefreshToken(refreshToken);
            // Update the status of the user being logged in, token
            user.setLoginStatus(1);
            user.setLastLogin(DateTimeUtil.getCurrentTimestamp());
            userRepository.saveAndFlush(user);
            //
            // notify the user has logged in
            chatService.notifyLogin(user.getEmpCd(), in.lang);
            //
            return dto;
        } else {
            throw new BusinessException(LOGIN_ERR_PASSWORD, in.lang);
        }
    }
}
