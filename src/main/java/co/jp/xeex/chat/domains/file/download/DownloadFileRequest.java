package co.jp.xeex.chat.domains.file.download;

import co.jp.xeex.chat.base.RequestBase;
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
public class DownloadFileRequest extends RequestBase {
    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    private String fileClassify;

    @NotNull(message = DtoValidateConsts.VALIDATE_ERR_NOT_NULL)
    @NotEmpty(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
    private String storeName;
}
