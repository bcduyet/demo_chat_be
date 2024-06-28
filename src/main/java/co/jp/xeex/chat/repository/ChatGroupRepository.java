package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDto;
import co.jp.xeex.chat.entity.ChatGroup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ChatGroupRepository
 * 
 * @author q_thinh
 */
@Repository
public interface ChatGroupRepository extends RepositoryBase<ChatGroup, String> {
    /**
     * Search group name with value
     * 
     * @param empcd       username
     * @param searchValue search group name value
     * @return
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.group.dto.ChatGroupDto(" +
            "   t1.id, " +
            "   t1.groupName) " +
            " FROM " +
            "   ChatGroup t1, " +
            "   ChatGroupMember t2 " +
            " WHERE " +
            "   t1.id = t2.groupId " +
            "   AND t2.memberEmpCd = :empCd " +
            "   AND t1.groupName LIKE %:searchValue% ")
    List<ChatGroupDto> findGroupByValue(@Param("empCd") String empcd,
            @Param("searchValue") String searchValue);

    /**
     * v_long:<br>
     * Get all chat groupIds that user is a member or friend <br>
     * (this use to notify to all chatGroup or friend of user when user is
     * online/offline)<br>
     * 
     * @param userName user name (emp_cd)
     * @return list of group_id *
     */
    @Query(value = "select distinct group_id " +
            "from ( " +
            "   select group_id " +
            "   from chat_group_member " +
            "   where member_emp_cd = :userName " +
            "   union " +
            "   select " +
            "   group_id from chat_friend  " +
            "   where :userName in (emp_cd_1,emp_cd_2) " +
            ") all_groups; ", nativeQuery = true)
    List<String> getUserGroupIdList(@Param("userName") String userName);

    /**
     * Get all user in group
     * (normal group or group friend)
     * 
     * @param groupId group id
     * @return list of empCd *
     */
    @Query(value = "SELECT DISTINCT empCd " +
            "FROM ( " +
            "   SELECT " +
            "     member_emp_cd AS empCd " +
            "   FROM chat_group_member " +
            "   WHERE group_id = :groupId " +
            "   UNION " +
            "   SELECT " +
            "     emp_cd_1 AS empCd " +
            "   FROM chat_friend  " +
            "   WHERE group_id = :groupId " +
            ") t", nativeQuery = true)
    List<String> getUserByGroupId(@Param("groupId") String groupId);
}
