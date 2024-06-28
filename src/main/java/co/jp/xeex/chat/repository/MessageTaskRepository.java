package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.MessageTask;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for MessageTask management.
 * 
 * @author q_thinh
 */
@Repository
public interface MessageTaskRepository extends RepositoryBase<MessageTask, String> {
    /**
     * Find all message task file by message id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT t1.id " +
            " FROM " +
            "   message_task t1, " +
            "   chat_message t2 " +
            " WHERE " +
            "   t1.message_id = t2.id " +
            "   AND t2.group_id = :groupId ", nativeQuery = true)
    List<String> getListIdByGroupId(@Param("groupId") String groupId);

    /**
     * Get message id by task id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT message_id " +
            " FROM " +
            "   message_task" +
            " WHERE " +
            "   task_id = :taskId " +
            " LIMIT 1 ", nativeQuery = true)
    String getMessageIdByTaskId(@Param("taskId") String taskId);

    /**
     * Get task id by message id
     * 
     * @param messageId
     * @return
     */
    @Query(value = "SELECT task_id " +
            " FROM " +
            "   message_task" +
            " WHERE " +
            "   message_id = :messageId " +
            " LIMIT 1 ", nativeQuery = true)
    String getTaskIdByMessageId(@Param("messageId") String messageId);

    /**
     * Find MessageTask by message id
     * 
     * @param messageId
     * @return
     */
    @Query(value = "SELECT * " +
            " FROM " +
            "   message_task" +
            " WHERE " +
            "   message_id = :messageId " +
            " LIMIT 1 ", nativeQuery = true)
    MessageTask findByMessageId(@Param("messageId") String messageId);

    /**
     * Find MessageTask by task id
     * 
     * @param messageId
     * @return
     */
    @Query(value = "SELECT * " +
            " FROM " +
            "   message_task" +
            " WHERE " +
            "   task_id = :taskId " +
            " LIMIT 1 ", nativeQuery = true)
    MessageTask findByTaskId(@Param("taskId") String taskId);
}
