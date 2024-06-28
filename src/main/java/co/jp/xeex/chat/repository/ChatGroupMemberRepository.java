package co.jp.xeex.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.ChatGroupMember;

/**
 * ChatGroupMemberRepository
 * 
 * @author q_thinh
 */
@Repository
public interface ChatGroupMemberRepository extends RepositoryBase<ChatGroupMember, String> {

    /**
     * Get list member of group
     * 
     * @param groupId group id
     * @return list member of group
     */
    @Query(value = "select * from chat_group_member where group_id = :groupId", nativeQuery = true)
    List<ChatGroupMember> findMembersByGroup(@Param("groupId") String groupId);
}
