package co.jp.xeex.chat.domains.admin.delete.user;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

//note: if it is not common, please do not declare it public
@Service
public class DeleteUserServiceImpl extends ServiceBaseImpl<DeleteUserRequest, DeleteUserResponse>
        implements DeleteUserService {
    // repository uses
    private final UserRepository repo;

    public DeleteUserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    protected DeleteUserResponse processRequest(DeleteUserRequest in) throws BusinessException {
        //in has been validated before calling here (null or empty) 
        repo.hardDeleteUsers(in.userIds);
        //not error, return empty response
        DeleteUserResponse out = new DeleteUserResponse();
        out.setSuccess(true);
        return out;
    }

}
