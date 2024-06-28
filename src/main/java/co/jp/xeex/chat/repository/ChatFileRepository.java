package co.jp.xeex.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.ChatFile;

/**
 * ChatFileRepository
 * 
 * @author q_thinh
 */
@Repository
public interface ChatFileRepository extends RepositoryBase<ChatFile, String> {
    /**
     * find all chat file id by group id
     * 
     * @param groupId
     * @return
     */
    @Query(value = "SELECT t1.* " +
            " FROM " +
            "   chat_file t1," +
            "   chat_message t2 " +
            " WHERE " +
            "   t1.message_id = t2.id " +
            "   AND t2.group_id = :groupId", nativeQuery = true)
    List<ChatFile> findByGroupId(@Param("groupId") String groupId);

    /**
     * Find all chat file by message id
     * 
     * @param messageId
     */
    @Query(value = "SELECT * FROM chat_file WHERE message_id = :messageId", nativeQuery = true)
    List<ChatFile> findAllByMessageId(@Param("messageId") String messageId);

    /**
     * Get list chat file id by message id
     * 
     * @param messageId
     */
    @Query(value = "SELECT id FROM chat_file WHERE message_id = :messageId", nativeQuery = true)
    List<String> getListIdByMessageId(@Param("messageId") String messageId);

    /**
     * Get chat file by file id
     * 
     * @param fileId
     * @return
     */
    @Query(value = "SELECT * FROM chat_file WHERE file_id = :fileId LIMIT 1", nativeQuery = true)
    ChatFile findByFileId(@Param("fileId") String fileId);
}
