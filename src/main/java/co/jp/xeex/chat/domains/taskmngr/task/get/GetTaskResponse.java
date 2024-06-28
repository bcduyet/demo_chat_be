package co.jp.xeex.chat.domains.taskmngr.task.get;

import java.util.List;

import co.jp.xeex.chat.domains.taskmngr.task.dto.TaskDto;
import lombok.Data;

/**
 * GetTaskResponse
 * 
 * @author q_thinh
 */
@Data
public class GetTaskResponse {
    private Integer totalTask;
    private List<TaskDto> tasks;
}
