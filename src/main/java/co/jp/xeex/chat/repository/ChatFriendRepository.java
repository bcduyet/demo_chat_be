package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDto;
import co.jp.xeex.chat.entity.ChatFriend;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * ChatFriendRepository
 * 
 * @author q_thinh
 */
@Repository
public interface ChatFriendRepository extends RepositoryBase<ChatFriend, String> {
    /**
     * Find friend detail by value
     * 
     * @param empcd
     * @param searchValue
     * @return
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.friend.dto.FriendDto(" +
            "   t.groupId, " +
            "   t.empCd2) " +
            " FROM " +
            "   ChatFriend t " +
            " WHERE " +
            "   t.empCd1 = :empCd " +
            "   AND t.empCd2 LIKE %:searchValue% ")
    List<FriendDto> findByValue(@Param("empCd") String empcd,
            @Param("searchValue") String searchValue);

    /**
     * Find friend
     * 
     * @param empCd1
     * @param empCd2
     * @return
     */
    @Query(value = "SELECT * " +
            " FROM " +
            "   chat_friend " +
            " WHERE " +
            "   (emp_cd_1 = :empCd1 AND emp_cd_2 = :empCd2) " +
            "   OR (emp_cd_1 = :empCd2 AND emp_cd_2 = :empCd1) ", nativeQuery = true)
    List<ChatFriend> findByCd(@Param("empCd1") String empCd1,
            @Param("empCd2") String empCd2);
}
