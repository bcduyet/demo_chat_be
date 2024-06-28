package co.jp.xeex.chat.domains.file.upload;

import org.springframework.web.multipart.MultipartFile;

import co.jp.xeex.chat.base.RequestBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UploadFileRequest
 * 
 * @author q_thinh
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UploadFileRequest extends RequestBase {
    private MultipartFile[] files;
    private boolean isAvatar = false;
}
