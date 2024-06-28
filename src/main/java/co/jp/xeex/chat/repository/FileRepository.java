package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.File;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The repository for File management.
 * 
 * @author q_thinh
 */
@Repository
public interface FileRepository extends RepositoryBase<File, String> {
    /**
     * Find file by list id
     * 
     * @param idList
     */
    @Query(value = "SELECT * FROM file WHERE id IN (:idList)", nativeQuery = true)
    List<File> findByIdList(@Param("idList") List<String> idList);

    /**
     * Get list file by messageId
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT " +
            "   t2.* " +
            " FROM " +
            "   chat_file t1, " +
            "   file t2 " +
            " WHERE " +
            "   t1.file_id = t2.id " +
            "   AND t1.message_id = :messageId", nativeQuery = true)
    List<File> findByMessageId(@Param("messageId") String messageId);

    /**
     * Get list file by taskId
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT " +
            "   t2.* " +
            " FROM " +
            "   task_file t1, " +
            "   file t2 " +
            " WHERE " +
            "   t1.file_id = t2.id " +
            "   AND t1.task_id = :taskId", nativeQuery = true)
    List<File> findByTaskId(@Param("taskId") String taskId);

    /**
     * Find file by store file name
     * 
     * @param storeName
     * @return
     */
    @Query(value = "SELECT * FROM file WHERE store_name = :storeName", nativeQuery = true)
    File findByStoreName(@Param("storeName") String storeName);

    /**
     * Get group by store name
     * 
     * @param storeName
     * @return
     */
    @Query(value = "SELECT DISTINCT groupId " +
            "FROM ( " +
            "   SELECT " +
            "     t3.group_id AS groupId " +
            "   FROM " +
            "     file t1, " +
            "     chat_file t2, " +
            "     chat_message t3 " +
            "   WHERE " +
            "     t1.id = t2.file_id " +
            "     AND t2.message_id = t3.id " +
            "     AND t1.store_name = :storeName " +
            "   UNION " +
            "   SELECT " +
            "     t6.group_id AS groupId " +
            "   FROM " +
            "     file t4,  " +
            "     task_file t5, " +
            "     task t6 " +
            "   WHERE " +
            "     t4.id = t5.file_id " +
            "     AND t5.task_id = t6.id " +
            "     AND t4.store_name = :storeName " +
            ") t", nativeQuery = true)
    String getGroupByStoreName(@Param("storeName") String storeName);

    /**
     * Get username by store name
     * 
     * @param storeName
     * @return
     */
    @Query(value = "SELECT createby FROM file WHERE store_name = :storeName", nativeQuery = true)
    String getUserNameByStoreName(@Param("storeName") String storeName);
}
