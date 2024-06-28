package co.jp.xeex.chat.domains.taskmngr.task.save;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskPriority;
import co.jp.xeex.chat.domains.taskmngr.task.enums.TaskStatus;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * SaveTaskRequest
 * 
 * @author q_thinh
 */
public class SaveTaskRequest extends RequestBase {
    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String groupId;

    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String messageId = StringUtils.EMPTY;

    @Pattern(regexp = DtoValidateConsts.PATTERN_UUID_STRING, message = DtoValidateConsts.VALIDATE_ERR_ID_UUID_INVALID)
    public String taskId = StringUtils.EMPTY;

    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    @Length(min = 1, max = 80, message = DtoValidateConsts.VALIDATE_ERR_LEN_MIN_MAX)
    public String taskName;

    @Pattern(regexp = DtoValidateConsts.PATTERN_DATE_STRING, message = DtoValidateConsts.VALIDATE_ERR_DATE_INVALID)
    public String startDate;

    @Pattern(regexp = DtoValidateConsts.PATTERN_DATE_STRING, message = DtoValidateConsts.VALIDATE_ERR_DATE_INVALID)
    public String dueDate;

    @Nullable
    public String assignee;

    @Nullable
    public String description;

    @Nullable
    public TaskStatus taskStatus;

    @Nullable
    public TaskPriority taskPriority;

    @Nullable
    public List<FileDto> taskFiles = new ArrayList<>();
}
