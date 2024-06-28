package co.jp.xeex.chat.domains.file.upload;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileController
 * 
 * @author q_thinh
 */
@RestController
public class UploadFileController extends ControllerBase<UploadFileRequest> {
    private final UploadFileService uploadFileService;

    public UploadFileController(@Autowired UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @Override
    public final ResponseEntity<?> restApi(@RequestBody UploadFileRequest request) throws BusinessException {
        return null;
    }

    @SuppressWarnings({ "squid:S1452" })
    @PostMapping(path = RestApiEndPoints.UPLOAD_FILE, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public final ResponseEntity<?> restFileApi(@RequestParam(value = "isAvatar", required = false) Boolean isAvatar,
            @RequestParam("files") MultipartFile[] files) throws Exception {

        UploadFileRequest request = new UploadFileRequest();
        super.preProcessRequest(request);
        request.setAvatar(isAvatar != null && isAvatar);
        request.setFiles(files);
        UploadFileResponse out = uploadFileService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
