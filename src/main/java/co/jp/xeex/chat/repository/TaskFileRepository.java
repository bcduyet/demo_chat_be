package co.jp.xeex.chat.repository;

import co.jp.xeex.chat.base.RepositoryBase;
import co.jp.xeex.chat.entity.TaskFile;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * The repository for TaskFile management.
 * 
 * @author q_thinh
 */
@Repository
public interface TaskFileRepository extends RepositoryBase<TaskFile, String> {
    /**
     * Get list task file id by task id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT id FROM task_file WHERE task_id = :taskId", nativeQuery = true)
    List<String> getListIdByTaskId(@Param("taskId") String taskId);

    /**
     * Find all task file by task id
     * 
     * @param taskId
     * @return
     */
    @Query(value = "SELECT * FROM task_file WHERE task_id = :taskId", nativeQuery = true)
    List<TaskFile> findAllByTaskId(@Param("taskId") String taskId);

    /**
     * Get task file by file id
     * 
     * @param fileId
     * @return
     */
    @Query(value = "SELECT * FROM task_file WHERE file_id = :fileId LIMIT 1", nativeQuery = true)
    TaskFile findByFileId(@Param("fileId") String fileId);
}
