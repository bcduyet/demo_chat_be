package co.jp.xeex.chat.domains.file.upload;

import co.jp.xeex.chat.base.ServiceBaseImpl;
import co.jp.xeex.chat.common.AppConstant;
import co.jp.xeex.chat.domains.file.dto.FileDto;
import co.jp.xeex.chat.domains.file.enums.FileType;
import co.jp.xeex.chat.entity.File;
import co.jp.xeex.chat.entity.User;
import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.repository.FileRepository;
import co.jp.xeex.chat.repository.UserRepository;
import co.jp.xeex.chat.util.EnvironmentUtil;
import co.jp.xeex.chat.util.FileUtil;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileServiceImpl
 * 
 * @author q_thinh
 */
@Service
@AllArgsConstructor
public class UploadFileServiceImpl extends ServiceBaseImpl<UploadFileRequest, UploadFileResponse>
        implements UploadFileService {

    // err message id
    private static final String UPLOAD_FILE_ERR_FILE_EMPTY = "UPLOAD_FILE_ERR_FILE_EMPTY";
    private static final String UPLOAD_FILE_ERR_EXCEEDED_AVATAR_SIZE = "UPLOAD_FILE_ERR_EXCEEDED_AVATAR_SIZE";
    private static final String UPLOAD_FILE_ERR_EXCEEDED_FILE_COUNT = "UPLOAD_FILE_ERR_EXCEEDED_FILE_COUNT";
    private static final String UPLOAD_FILE_ERR_FILE_TYPE_IMAGE_INCORRECT = "UPLOAD_FILE_ERR_FILE_TYPE_IMAGE_INCORRECT";

    // constants
    private static final String ENV_LIMIT_AVATAR_SIZE_UPLOAD_KEY = "file.limit-avatar-size-upload";
    private static final String ENV_LIMIT_FILE_COUNT_UPLOAD_KEY = "file.limit-file-count-upload";
    private static final String[] IMAGE_TYPES = new String[] { "png", "jpg", "jpeg", "svg" };

    // DI
    private UserRepository userRepo;
    private FileRepository fileRepo;
    private EnvironmentUtil environmentUtil;

    @Override
    @Transactional(rollbackFor = { Exception.class, BusinessException.class })
    public UploadFileResponse processRequest(UploadFileRequest in) throws BusinessException {
        // Validation files
        validation(in);

        // Setting upload path
        String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
        Path targetUploadPath = in.isAvatar()
                ? Paths.get(envRootUploadPath, AppConstant.PATH_AVATAR_PREFIX, in.requestBy)
                : Paths.get(envRootUploadPath, AppConstant.PATH_TEMP_PREFIX, in.requestBy);

        // Upload files
        String domain = environmentUtil.getDomain();
        List<FileDto> files = new ArrayList<>();
        for (MultipartFile multipartFile : in.getFiles()) {
            String generateStoreFileName;
            try {
                generateStoreFileName = FileUtil.uploadFile(targetUploadPath, multipartFile);
            } catch (IOException e) {
                throw new BusinessException(e.getMessage(), in.lang);
            }

            // Save avatar when public
            FileDto fileDto = new FileDto();
            if (in.isAvatar()) {
                // Save avatar
                User user = userRepo.findByUserName(in.requestBy);
                String oldAvatar = user.getAvatar();
                user.setAvatar(generateStoreFileName);
                userRepo.saveAndFlush(user);

                // Delete old avatar
                try {
                    deleteOldAvatar(oldAvatar);
                } catch (IOException e) {
                    throw new BusinessException(e.getMessage(), in.lang);
                }

                // Save new avatar file
                File file = new File();
                file.initDefault(in.requestBy);
                file.setStoreName(generateStoreFileName);
                file.setOriginName(multipartFile.getOriginalFilename());
                file.setFileType(FileType.IMAGE);
                fileRepo.saveAndFlush(file);

                // Link avatar
                String fileUrl = String.format(AppConstant.FILE_URL, domain, AppConstant.PATH_AVATAR_PREFIX,
                        generateStoreFileName);
                fileDto.setImageUrl(fileUrl);
            }

            fileDto.setFileType(FileUtil.isImage(multipartFile) ? FileType.IMAGE : FileType.FILE);
            fileDto.setOriginName(multipartFile.getOriginalFilename());
            fileDto.setStoreName(generateStoreFileName);
            fileDto.setEmpCd(in.requestBy);
            files.add(fileDto);
        }

        // Response
        UploadFileResponse response = new UploadFileResponse();
        response.setFiles(files);
        return response;
    }

    /**
     * Validation files
     * 
     * @param in
     * @throws BusinessException
     */
    private void validation(UploadFileRequest in) throws BusinessException {
        // Validation files
        if (in.getFiles()[0].isEmpty()) {
            throw new BusinessException(UPLOAD_FILE_ERR_FILE_EMPTY, in.lang);
        }

        // Check max files
        Integer maxCountUpload = Integer.parseInt(environmentUtil.getConfigValue(ENV_LIMIT_FILE_COUNT_UPLOAD_KEY));
        if (in.getFiles().length > maxCountUpload) {
            throw new BusinessException(UPLOAD_FILE_ERR_EXCEEDED_FILE_COUNT,
                    new String[] { maxCountUpload.toString() }, in.lang);
        }

        // Validation when upload avatar
        if (in.isAvatar()) {
            for (MultipartFile file : in.getFiles()) {
                // Check image type
                if (!FileUtil.isImage(file)) {
                    throw new BusinessException(UPLOAD_FILE_ERR_FILE_TYPE_IMAGE_INCORRECT,
                            new String[] { String.join(",", IMAGE_TYPES) }, in.lang);
                }

                // Check avatar size
                Float fileSize = file.getSize() / 1000000F;
                Float maxSizeAllow = Float.parseFloat(environmentUtil.getConfigValue(ENV_LIMIT_AVATAR_SIZE_UPLOAD_KEY));
                if (fileSize > maxSizeAllow) {
                    throw new BusinessException(UPLOAD_FILE_ERR_EXCEEDED_AVATAR_SIZE,
                            new String[] { maxSizeAllow.toString() }, in.lang);
                }
            }
        }
    }

    /**
     * delete old avatar
     * 
     * @param oldAvatar
     * @throws IOException
     */
    private void deleteOldAvatar(String oldAvatar) throws IOException {
        // Delete old avatar
        File oldAvatarFile = fileRepo.findByStoreName(oldAvatar);
        if (oldAvatarFile != null) {
            fileRepo.delete(oldAvatarFile);

            // Delete store file
            String envRootUploadPath = environmentUtil.getConfigValue(AppConstant.ENV_PATH_UPLOAD_KEY);
            Path targetPath = FileUtil.getTargetPath(envRootUploadPath, AppConstant.PATH_AVATAR_PREFIX,
                    oldAvatarFile.getCreateBy(), null);
            targetPath = targetPath.resolve(Paths.get(oldAvatar)).normalize().toAbsolutePath();
            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
            }
        }
    }
}
