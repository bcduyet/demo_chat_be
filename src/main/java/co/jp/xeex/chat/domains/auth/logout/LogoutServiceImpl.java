package co.jp.xeex.chat.domains.auth.logout;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chat.ChatService;
import co.jp.xeex.chat.domains.chat.SocketSessionStorage;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Log4j
public class LogoutServiceImpl extends ServiceBaseImpl<LogoutRequest, LogoutResponse> implements LogoutService {
    private static final String LOG_OUT_ERR_USER_NOT_EXIST = null;

    // DI
    private UserRepository userRepository;
    private ChatService chatService;
    private EnvironmentUtil environmentUtil;

    @Override
    protected LogoutResponse processRequest(LogoutRequest in) throws BusinessException {
        String userName = in.requestBy;
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new BusinessException(LOG_OUT_ERR_USER_NOT_EXIST, in.lang);
        }
        user.setLoginStatus(0);
        userRepository.saveAndFlush(user);

        // Delete temp path file by username
        try {
            String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
            Path tempPath = FileUtil.getTargetPath(envRootUploadPath, AppConstant.PATH_TEMP_PREFIX, userName, null);
            FileUtil.deletePath(tempPath);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), in.lang);
        }

        // notify to public chat groupt
        chatService.notifyLogout(userName, in.lang);
        // close session
        try {
            SocketSessionStorage.closeSessions(userName);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return new LogoutResponse();
    }

}
