package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * The repository for Task management.
 * 
 * @author q_thinh
 */
@Repository
public interface TaskRepository extends RepositoryBase<Task, String> {
    /**
     * Get task by value per page
     * 
     * @param groupIds
     * @param groupId
     * @param empCd
     * @param searchValue
     * @param page
     * @return
     */
    @Query(value = "SELECT t.* FROM( " +
            " SELECT " +
            "   t1.* " +
            " FROM " +
            "   task t1, " +
            "   chat_group t2, " +
            "   dfw_m050m t3 " +
            " WHERE " +
            "   ( t1.createby = t3.emp_cd OR t1.assignee = t3.emp_cd )" +
            "   AND ( t1.group_id = t2.id OR ( t1.group_id IS NULL AND ( :empCd IN ( t1.createby, t1.assignee ) ) ) )" +
            "   AND ( t1.group_id IN :groupIds OR t1.group_id IS NULL ) " +
            "   AND ( :groupId = '' OR t1.group_id = :groupId ) " +
            "   AND ( :searchValue = '' " +
            "     OR t1.task_name LIKE %:searchValue% " +
            "     OR ( t1.group_id IS NOT NULL AND t2.group_name LIKE %:searchValue% )" +
            "     OR t1.assignee LIKE %:searchValue% " +
            "     OR t1.createby LIKE %:searchValue% " +
            "     OR t3.full_name LIKE %:searchValue% )  " +
            " GROUP BY t1.id " +
            " ORDER BY t1.createat DESC ) t", countQuery = "SELECT count(*) FROM task", nativeQuery = true)
    Page<Task> getTasksByValue(@Param("groupIds") List<String> groupIds,
            @Param("groupId") String groupId,
            @Param("empCd") String empCd,
            @Param("searchValue") String searchValue,
            PageRequest page);

    /**
     * Count task by value
     * 
     * @param groupIds
     * @param groupId
     * @param empCd
     * @param searchValue
     * @return
     */
    @Query(value = "SELECT COUNT(*) FROM " +
            " ( SELECT " +
            "   t1.* " +
            " FROM " +
            "   task t1, " +
            "   chat_group t2, " +
            "   dfw_m050m t3 " +
            " WHERE " +
            "   ( t1.createby = t3.emp_cd OR t1.assignee = t3.emp_cd )" +
            "   AND ( t1.group_id = t2.id OR ( t1.group_id IS NULL AND ( :empCd IN ( t1.createby, t1.assignee ) ) ) )" +
            "   AND ( t1.group_id IN :groupIds OR t1.group_id IS NULL ) " +
            "   AND ( :groupId = '' OR t1.group_id = :groupId ) " +
            "   AND ( :searchValue = '' " +
            "     OR t1.task_name LIKE %:searchValue% " +
            "     OR ( t1.group_id IS NOT NULL AND t2.group_name LIKE %:searchValue% ) " +
            "     OR t1.assignee LIKE %:searchValue% " +
            "     OR t1.createby LIKE %:searchValue% " +
            "     OR t3.full_name LIKE %:searchValue% )  " +
            " GROUP BY t1.id ) t  ", nativeQuery = true)
    Integer countByValue(@Param("groupIds") List<String> groupIds,
            @Param("groupId") String groupId,
            @Param("empCd") String empCd,
            @Param("searchValue") String searchValue);
}
