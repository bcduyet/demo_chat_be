package co.jp.xeex.chat.domains.auth.changepwd;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.encryption.EncodeService;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.token.JwtTokenService;
import org.springframework.stereotype.Service;

@Service
public class ChangePwdServiceImpl extends ServiceBaseImpl<ChangePwdRequest, ChangePwdResponse>
        implements ChangePwdService {
    private static final String CHANGEPWD_ERR_OLD_PASSWORD_NOT_MATCH = "CHANGEPWD_ERR_OLD_PASSWORD_NOT_MATCH";
    private static final String CHANGEPWD_ERR_USER_IS_NOT_OWNER = "CHANGEPWD_ERR_USER_IS_NOT_OWNER";
    private static final String CHANGEPWD_ERR_USER_IS_NOT_EXISTED = "CHANGEPWD_ERR_USER_IS_NOT_EXISTED";
    private static final String CHANGE_PASSWORD_ERR_CONFIRM_PASSWORD_NOT_MATCH = "CHANGE_PASSWORD_ERR_CONFIRM_PASSWORD_NOT_MATCH";
    private final UserRepository userRepository;
    private final EncodeService encoder;

    public ChangePwdServiceImpl(UserRepository userRepository, EncodeService encoder, JwtTokenService jwtTokenService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    protected ChangePwdResponse processRequest(ChangePwdRequest request) throws BusinessException {
        // check if new password and confirm password are the same
        if (!request.newPassword.equals(request.confirmPassword)) {
            throw new BusinessException(CHANGE_PASSWORD_ERR_CONFIRM_PASSWORD_NOT_MATCH, request.lang);
        }

        // check if user is owner
        if (!request.userName.equals(request.requestBy)) {
            throw new BusinessException(CHANGEPWD_ERR_USER_IS_NOT_OWNER, request.lang);
        }

        // check if user already exists?
        User user = userRepository.findByUserName(request.userName);
        if (user == null) {
            throw new BusinessException(CHANGEPWD_ERR_USER_IS_NOT_EXISTED, request.lang);
        }

        // check if old password is correct
        if (!encoder.matches(request.oldPassword, user.getPassword())) {
            throw new BusinessException(CHANGEPWD_ERR_OLD_PASSWORD_NOT_MATCH, request.lang);
        }

        // update password
        user.setPassword(encoder.encode(request.newPassword));
        userRepository.saveAndFlush(user);

        // create new token and set to response
        ChangePwdResponse response = new ChangePwdResponse();
        response.setResult(true);
        return response;
    }
}
