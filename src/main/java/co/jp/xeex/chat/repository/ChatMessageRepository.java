package co.jp.xeex.chat.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto;
import co.jp.xeex.chat.domains.chatmngr.msg.dto.SenderDto;
import co.jp.xeex.chat.domains.chatmngr.thread.dto.ThreadGroupDto;
import co.jp.xeex.chat.entity.ChatMessage;

/**
 * ChatMessageRepository
 * 
 * @author v_long, q_thinh
 */
@Repository
public interface ChatMessageRepository extends RepositoryBase<ChatMessage, String> {
    /**
     * find list chat message detail by group
     * 
     * @param groupId
     * @param limitMessage
     * @return List<ChatMessage>
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto(" +
            "   t1.groupId, " +
            "   t1.id, " +
            "   t1.repplyMessageId, " +
            "   t1.createBy, " +
            "   t2.avatar, " +
            "   t2.fullName, " +
            "   t1.createAt, " +
            "   t1.chatContent, " +
            "   t1.action) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.groupId = :groupId " +
            "   AND t1.repplyMessageId IS NULL " +
            " ORDER BY t1.createAt DESC " +
            " LIMIT :limitMessage ")
    List<ChatMessageDetailDto> findByGroup(@Param("groupId") String groupId,
            @Param("limitMessage") Integer limitMessage);

    /**
     * find list chat message detail by group and chat time
     * 
     * @param groupId
     * @param chatTime
     * @param limitMessage
     * @return List<ChatMessage>
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto(" +
            "   t1.groupId, " +
            "   t1.id, " +
            "   t1.repplyMessageId, " +
            "   t1.createBy, " +
            "   t2.avatar, " +
            "   t2.fullName, " +
            "   t1.createAt, " +
            "   t1.chatContent, " +
            "   t1.action) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.groupId = :groupId " +
            "   AND t1.repplyMessageId IS NULL " +
            "   AND t1.createAt < :chatTime " +
            " ORDER BY t1.createAt DESC " +
            " LIMIT :limitMessage ")
    List<ChatMessageDetailDto> findByValue(@Param("groupId") String groupId,
            @Param("chatTime") Timestamp chatTime,
            @Param("limitMessage") Integer limitMessage);

    /**
     * find repply message id by group
     * 
     * @param groupId
     * @return list repply message id
     */
    @Query(value = "SELECT " +
            " repply_message_id " +
            " FROM " +
            "   chat_message " +
            " WHERE " +
            "   group_id = :groupId " +
            "   AND repply_message_id IS NOT NULL " +
            " GROUP BY repply_message_id ", nativeQuery = true)
    List<String> findRepplyMessageIdByGroup(@Param("groupId") String groupId);

    /**
     * find repply message by repply message id
     * 
     * @param repplyMsgId
     * @param limitMessage
     * @return List<ChatMessage>
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto(" +
            "   t1.groupId, " +
            "   t1.id, " +
            "   t1.repplyMessageId, " +
            "   t1.createBy, " +
            "   t2.avatar, " +
            "   t2.fullName, " +
            "   t1.createAt, " +
            "   t1.chatContent, " +
            "   t1.action) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.repplyMessageId = :repplyMsgId " +
            " ORDER BY t1.createAt DESC " +
            " LIMIT :limitMessage ")
    List<ChatMessageDetailDto> findRepplyMessageById(@Param("repplyMsgId") String repplyMsgId,
            @Param("limitMessage") Integer limitMessage);

    /**
     * find list chat message by group and chat time
     * 
     * @param groupId
     * @param chatTime
     * @param limitMessage
     * @return List<ChatMessage>
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto(" +
            "   t1.groupId, " +
            "   t1.id, " +
            "   t1.repplyMessageId, " +
            "   t1.createBy, " +
            "   t2.avatar, " +
            "   t2.fullName, " +
            "   t1.createAt, " +
            "   t1.chatContent, " +
            "   t1.action) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.repplyMessageId = :repplyMsgId " +
            "   AND t1.createAt < :chatTime " +
            " ORDER BY t1.createAt DESC " +
            " LIMIT :limitMessage ")
    List<ChatMessageDetailDto> findRepplyMessageByValue(@Param("repplyMsgId") String repplyMsgId,
            @Param("chatTime") Timestamp chatTime,
            @Param("limitMessage") Integer limitMessage);

    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.ChatMessageDetailDto(" +
            "   t1.groupId, " +
            "   t1.id, " +
            "   t1.repplyMessageId, " +
            "   t1.createBy, " +
            "   t2.avatar, " +
            "   t2.fullName, " +
            "   t1.createAt, " +
            "   t1.chatContent, " +
            "   t1.action) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.id = :messageId ")
    ChatMessageDetailDto findMessageDetailById(@Param("messageId") String messageId);

    /**
     * find repply user by repply message id
     * 
     * @param repplyMsgId
     * @return list user
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.msg.dto.SenderDto(" +
            " t2.empCd, " +
            " t2.avatar, " +
            " t2.fullName) " +
            " FROM " +
            "   ChatMessage t1, " +
            "   User t2 " +
            " WHERE " +
            "   t1.createBy = t2.empCd " +
            "   AND t1.repplyMessageId = :repplyMsgId " +
            " GROUP BY t1.createBy ")
    List<SenderDto> findRepplyUserById(@Param("repplyMsgId") String repplyMsgId);

    /**
     * find thread group by user name
     * 
     * @param empCd
     * @return list thread group
     */
    @Query(value = "SELECT new co.jp.xeex.chat.domains.chatmngr.thread.dto.ThreadGroupDto(" +
            "   t1.id, " +
            "   t1.groupName, " +
            "   t2.repplyMessageId, " +
            "   MAX(t2.createAt) AS lastChatTime) " +
            " FROM " +
            "   ChatGroup t1, " +
            "   ChatMessage t2 " +
            " WHERE " +
            "   t1.id = t2.groupId " +
            "   AND t2.createBy = :empCd " +
            "   AND t2.repplyMessageId IS NOT NULL " +
            " GROUP BY t1.id, t1.groupName, t2.repplyMessageId " +
            " ORDER BY lastChatTime DESC ")
    List<ThreadGroupDto> findThreadByUser(@Param("empCd") String empCd);

    /**
     * Get list message id by group
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT id FROM chat_message WHERE group_id = :groupId ", nativeQuery = true)
    List<String> getListIdByGroup(@Param("groupId") String groupId);

    /**
     * v_long<br>
     * get repply count of a message
     * 
     * @param messageId message id that existed in chat_message table
     * @return repply count
     */
    @Query(value = "SELECT COUNT(*) " +
            " FROM " +
            "   chat_message " +
            " WHERE " +
            "   repply_message_id = :messageId ", nativeQuery = true)
    int getRepplyCount(@Param("messageId") String messageId);

    /**
     * get last repply message by id
     * 
     * @param repplyMsgId
     * @return last repply
     */
    @Query(value = "SELECT " +
            "   MAX(createat) AS lastRepply " +
            "   FROM " +
            "     chat_message " +
            "   WHERE " +
            "     repply_message_id = :repplyMsgId ", nativeQuery = true)
    Timestamp getLastRepplyMessageById(@Param("repplyMsgId") String repplyMsgId);

    /**
     * get start message id in group
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT id " +
            "   FROM " +
            "     chat_message " +
            "   WHERE " +
            "     group_id = :groupId " +
            "   ORDER BY createat ASC " +
            "   LIMIT 1 ", nativeQuery = true)
    String getStartMessageIdByGroup(@Param("groupId") String groupId);

    /**
     * get start repply message id
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT id " +
            "   FROM " +
            "     chat_message " +
            "   WHERE " +
            "     repply_message_id = :repplyMsgId " +
            "   ORDER BY createat ASC " +
            "   LIMIT 1 ", nativeQuery = true)
    String getStartMessageByRepplyId(@Param("repplyMsgId") String repplyMsgId);

    /**
     * Get message by task id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT t1.* " +
            " FROM " +
            "   chat_message t1, " +
            "   message_task t2 " +
            " WHERE " +
            "   t1.id = t2.message_id " +
            "   AND t2.task_id = :taskId ", nativeQuery = true)
    ChatMessage findByTaskId(@Param("taskId") String taskId);

    /**
     * Get message by task id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT * " +
            " FROM " +
            "   chat_message " +
            " WHERE " +
            "   id = :messageId " +
            "   AND chat_content = '' ", nativeQuery = true)
    ChatMessage findMessageEmpty(@Param("messageId") String messageId);
}
