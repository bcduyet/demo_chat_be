package co.jp.xeex.chat.domains.admin.search.user;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.domains.admin.dto.UserDto;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

//note: if it is not common, please do not declare it public
@Service
public class SearchUserServiceImpl extends ServiceBaseImpl<SearchUserRequest, SearchUserResponse>
        implements SearchUserService {

    private static final String REST_API_WARN_NODATA_400 = "REST_API_WARN_NODATA_400";
    // repository uses
    private final UserRepository repo;// change with UseCaseRepository

    public SearchUserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    protected SearchUserResponse processRequest(SearchUserRequest in) throws BusinessException {
        List<User> users = repo.findUsers(
                in.id == null ? StringUtils.EMPTY : in.id,
                "".equals(in.userName) ? null : in.userName,
                "".equals(in.deptCd) ? null : in.deptCd,
                "".equals(in.email) ? null : in.email,
                "".equals(in.fullName) ? null : in.fullName,
                null == in.activeFlag ? -1 : in.activeFlag,
                null == in.useFlag ? -1 : in.useFlag);
        if (users == null || users.isEmpty()) {
            throw new BusinessException(REST_API_WARN_NODATA_400, in.lang);
        }
        List<UserDto> userDtos = users.stream().map(o -> {
            UserDto dto = new UserDto();
            dto.setId(o.getId());
            dto.setUserName(o.getEmpCd());
            dto.setDeptCd(o.getDeptCd());
            dto.setEmail(o.getEmail());
            dto.setFullName(o.getFullName());
            dto.setUseFlag(o.getUseFlag());
            dto.setActiveFlag(o.getActiveFlag());
            return dto;
        }).collect(Collectors.toList());
        SearchUserResponse out = new SearchUserResponse();
        out.setCount(users.size());
        out.setUsers(userDtos);
        return out;
    }

}
