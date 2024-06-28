package co.jp.xeex.chat.domains.file.delete;

import co.jp.xeex.chat.base.RequestBase;
import co.jp.xeex.chat.domains.file.enums.FileClassify;
import co.jp.xeex.chat.validation.DtoValidateConsts;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeleteFileRequest extends RequestBase {
    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    private String storeName;

    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    private FileClassify fileClassify;
}
