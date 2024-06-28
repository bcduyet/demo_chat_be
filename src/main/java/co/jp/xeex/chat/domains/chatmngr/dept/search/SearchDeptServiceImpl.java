package co.jp.xeex.chat.domains.chatmngr.dept.search;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.chatmngr.dept.dto.DeptDetailDto;
import co.jp.xeex.chat.domains.chatmngr.dept.dto.DeptDto;
import co.jp.xeex.chat.domains.chatmngr.group.dto.MemberDto;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.DepartmentRepository;
import co.jp.xeex.chat.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * SearchDeptServiceImpl
 * 
 * @author q_thinh
 */
@Service
public class SearchDeptServiceImpl extends ServiceBaseImpl<SearchDeptRequest, SearchDeptResponse>
        implements SearchDeptService {

    // DI
    private final DepartmentRepository departmentRepo;
    private final UserRepository userRepo;

    public SearchDeptServiceImpl(DepartmentRepository departmentRepo,
            UserRepository userRepo) {
        this.departmentRepo = departmentRepo;
        this.userRepo = userRepo;
    }

    @Override
    public SearchDeptResponse processRequest(SearchDeptRequest in) throws BusinessException {
        // Set searchValue
        in.setSearchValue((in.getSearchValue() == null || AppConstant.STAR_CHARACTER.equals(in.getSearchValue()))
                ? StringUtils.EMPTY
                : in.getSearchValue());

        // Search depts
        List<DeptDetailDto> deptDetails = getDeptDetails(in.getSearchValue());

        // Response
        SearchDeptResponse response = new SearchDeptResponse();
        response.setDepts(deptDetails);
        return response;
    }

    /**
     * getDeptDetails
     * 
     * @param searchValue
     * @return
     */
    private List<DeptDetailDto> getDeptDetails(String searchValue) {
        List<DeptDetailDto> result = new ArrayList<>();

        List<DeptDto> deptDtos = departmentRepo.findByValue(searchValue);
        for (DeptDto deptDto : deptDtos) {
            // Search member
            List<MemberDto> members = userRepo.findByDept(deptDto.getDeptCd());

            DeptDetailDto deptInfoDetailDto = new DeptDetailDto();
            deptInfoDetailDto.setDeptCd(deptDto.getDeptCd());
            deptInfoDetailDto.setDeptName(deptDto.getDeptName());
            deptInfoDetailDto.setMembers(members);
            result.add(deptInfoDetailDto);
        }

        return result;
    }
}
