package co.jp.xeex.chat.domains.file.upload;

import java.util.List;

import co.jp.xeex.chat.domains.file.dto.FileDto;
import lombok.Data;

/**
 * UploadFileResponse
 * 
 * @author q_thinh
 */
@Data
public class UploadFileResponse {
    List<FileDto> files;
}
