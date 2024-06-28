package co.jp.xeex.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.domains.chatmngr.dept.dto.DeptDto;
import co.jp.xeex.chat.entity.Department;

/**
 * DepartmentRepository
 * 
 * @author q_thinh
 */
@Repository
public interface DepartmentRepository extends RepositoryBase<Department, String> {
    /**
     * Find department by dept name
     * 
     * @param searchValue
     * @return list Department
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.dept.dto.DeptDto(" +
            " t.companyCd, " +
            " t.deptName) " +
            " FROM " +
            "   Department t " +
            " WHERE " +
            "   t.deptF = true " +
            "   AND t.deptName LIKE %:searchValue% ")
    List<DeptDto> findByValue(@Param("searchValue") String searchValue);

}
