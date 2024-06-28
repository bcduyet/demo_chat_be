package co.jp.xeex.chat.domains.usecase;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;
import org.springframework.stereotype.Service;

//note: if it is not common, please do not declare it public
@Service
public class UseCaseServiceImpl extends ServiceBaseImpl<UseCaseRequest, UseCaseResponse>
        implements UseCaseService {

    // repository uses
    // private final UserRepository repo;// change with UseCaseRepository

    public UseCaseServiceImpl(UserRepository repo) {
        // this.repo = repo;
    }

    @Override
    protected UseCaseResponse processRequest(UseCaseRequest in) throws BusinessException {
        // Implement the business logic here
        return null;
    }

}
