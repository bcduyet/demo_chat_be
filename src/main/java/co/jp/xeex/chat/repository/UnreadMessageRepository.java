package co.jp.xeex.chat.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.UnreadMessage;

/**
 * Repository for unread messages
 * 
 * @author v_long
 */
@Repository
public interface UnreadMessageRepository extends RepositoryBase<UnreadMessage, String> {
    /**
     * get the unread message by userId and chatGroupId
     * @param userId the user id
     * @param chatGroupId the chat group id
     * @return the unread message containing the userId, chatGroupId and chat unread count
     */
    @Query("SELECT o FROM UnreadMessage  o WHERE userId = :userId AND chatGroupId = :chatGroupId")
    UnreadMessage getUnreadMessage(@Param("userId") String userId, @Param("chatGroupId") String chatGroupId);

}