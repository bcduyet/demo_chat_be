package co.jp.xeex.chat.domains.file.download;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

/**
 * DownloadFile controller
 * 
 * @author q_thinh
 */
@RestController
public class DownloadFileController extends ControllerBase<DownloadFileRequest> {
    private final DownloadFileService downloadFileService;

    public DownloadFileController(DownloadFileService downloadFileService) {
        this.downloadFileService = downloadFileService;
    }

    @Override
    public ResponseEntity<?> restApi(DownloadFileRequest request) throws BusinessException {
        return null;
    }

    @GetMapping(RestApiEndPoints.DOWNLOAD_FILE)
    public final ResponseEntity<StreamingResponseBody> restFileApi(@PathVariable String fileClassify,
            @PathVariable String storeName) throws BusinessException {

        // Setting value
        DownloadFileRequest request = new DownloadFileRequest();
        request.setFileClassify(fileClassify);
        request.setStoreName(storeName);
        // super.preProcessRequest(request);
        DownloadFileResponse out = downloadFileService.execute(request);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment;filename=\"%s\"", out.getOriginName()));

        return ResponseEntity
                .ok()
                .headers(responseHeaders)
                .body(out.getStreamData());
    }
}
