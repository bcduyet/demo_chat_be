package co.jp.xeex.chat.domains.admin.save.user;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.encryption.EncodeService;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveUserServiceImpl extends ServiceBaseImpl<SaveUserRequest, SaveUserResponse> implements SaveUserService {
    private final UserRepository userRepository;
    private final EncodeService encoder;

    public SaveUserServiceImpl(UserRepository userRepository, EncodeService encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    protected SaveUserResponse processRequest(SaveUserRequest in) throws BusinessException {
        // check if user already exists
        User user = userRepository.findByUserName(in.userName);
        if (null == user) {
            user = new User();
        }

        user.setEmpCd(in.userName);
        user.setDeptCd(in.deptCd);
        user.setEmail(in.email);
        user.setFullName(in.fullName);
        user.setActiveFlag(in.activeFlag);
        user.setPassword(encoder.encode(in.password));
        user.initDefault(in.requestBy);

        User newUser = userRepository.saveAndFlush(user);
        SaveUserResponse out = new SaveUserResponse();
        out.setUsrId(newUser.getId());
        out.setUserName(newUser.getEmpCd());
        out.setDeptCd(newUser.getDeptCd());
        out.setEmail(newUser.getEmail());
        out.setFullName(newUser.getFullName());
        out.setActiveFlag(newUser.getActiveFlag());
        return out;
    }

}
