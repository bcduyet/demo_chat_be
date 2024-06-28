package co.jp.xeex.chat.domains.file.download;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import lombok.Data;

/**
 * @author q_thinh
 */
@Data
public class DownloadFileResponse {
    private StreamingResponseBody streamData;
    private String originName;
}
