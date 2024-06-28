package co.jp.xeex.chat.domains.file.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;

/**
 * DeleteFile controller
 * 
 * @author q_thinh
 */
@RestController
public class DeleteFileController extends ControllerBase<DeleteFileRequest> {
    private final DeleteFileService deleteFileService;

    public DeleteFileController(DeleteFileService deleteFileService) {
        this.deleteFileService = deleteFileService;
    }

    @Override
    @PostMapping(RestApiEndPoints.DELETE_FILE)
    public ResponseEntity<?> restApi(@RequestBody DeleteFileRequest request) throws BusinessException {
        super.preProcessRequest(request);
        DeleteFileResponse out = deleteFileService.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
