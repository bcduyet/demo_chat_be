package co.jp.xeex.chat.domains.file.download;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Timestamp;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DownloadFileServiceImpl implements DownloadFileService {

    // Error keys
    private static final String DOWNLOAD_FILE_ERR_FILE_IS_NOT_EXISTED = "DOWNLOAD_FILE_ERR_FILE_IS_NOT_EXISTED";

    // DI
    private FileRepository fileRepo;
    private EnvironmentUtil environmentUtil;

    @Override
    public DownloadFileResponse execute(DownloadFileRequest in) throws BusinessException {
        // Validation file
        File file = fileRepo.findByStoreName(in.getStoreName());
        if (file == null) {
            throw new BusinessException(DOWNLOAD_FILE_ERR_FILE_IS_NOT_EXISTED, in.lang);
        }

        // Get target path
        Path targePath = getTargetPath(in, file.getCreateAt());

        // Check exist file
        if (!Files.exists(targePath.resolve(in.getStoreName()))) {
            throw new BusinessException(DOWNLOAD_FILE_ERR_FILE_IS_NOT_EXISTED, in.lang);
        }

        // Get and check file
        StreamingResponseBody stream;
        try {
            stream = FileUtil.readFileStreamContent(targePath, in.getStoreName());
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), in.lang);
        }

        // Response
        DownloadFileResponse response = new DownloadFileResponse();
        response.setOriginName(file.getOriginName());
        response.setStreamData(stream);
        return response;
    }

    /**
     * Get target path
     * 
     * @param in
     * @param fileTimestamp
     * @return
     */
    private Path getTargetPath(DownloadFileRequest in, Timestamp fileTimestamp) {
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);

        // Get targetPath
        String targetPath = fileRepo.getUserNameByStoreName(in.getStoreName());
        if (!AppConstant.PATH_AVATAR_PREFIX.equalsIgnoreCase(in.getFileClassify())) {
            String groupId = fileRepo.getGroupByStoreName(in.getStoreName());
            targetPath = groupId == null ? targetPath : groupId;
        } else {
            fileTimestamp = null;
        }

        return FileUtil.getTargetPath(envRootUploadPath, in.getFileClassify(), targetPath, fileTimestamp);
    }
}
