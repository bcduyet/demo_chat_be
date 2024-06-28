package co.jp.xeex.chat.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.sql.Timestamp;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import co.jp.xeex.chat.common.AppConstant;

/**
 * FileUtil
 * 
 * @author q_thinh
 */
@SuppressWarnings({ "squid:S1118" })
public class FileUtil {
    private static final int BUFFER_SIZE = 1024;
    private static final String[] IMAGE_TYPES = new String[] { "png", "jpg", "jpeg", "svg" };

    /**
     * uploadFile to string path
     * 
     * @param uploadPath
     * @param file
     * @return
     * @throws IOException
     */
    public static String uploadFile(String uploadPath, MultipartFile file) throws IOException {
        return uploadFile(Paths.get(uploadPath), file);
    }

    /**
     * uploadFile to path
     * 
     * @param uploadPath
     * @param file
     * @return
     * @throws IOException
     */
    public static String uploadFile(Path uploadPath, MultipartFile file) throws IOException {
        // Create directory
        Files.createDirectories(uploadPath);

        // Generated store file name
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generateStoreName = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY) + "."
                + fileExtension;
        Path destPath = uploadPath.resolve(Paths.get(generateStoreName)).normalize().toAbsolutePath();

        // Copy file to uploads folder
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destPath, StandardCopyOption.REPLACE_EXISTING);
        }

        return generateStoreName;
    }

    /**
     * readFileByteContent
     * 
     * @param path
     * @param storeName
     * @return
     * @throws IOException
     */
    public static byte[] readFileByteContent(String path, String storeName) throws IOException {
        return readFileByteContent(Paths.get(path), storeName);
    }

    /**
     * readFileByteContent
     * 
     * @param path
     * @param storeName
     * @return
     * @throws IOException
     */
    public static byte[] readFileByteContent(Path path, String storeName) throws IOException {
        // Get file
        Path file = path.resolve(storeName);

        UrlResource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return StreamUtils.copyToByteArray(resource.getInputStream());
        }

        return new byte[0];
    }

    /**
     * readFileStreamContent
     * 
     * @param path
     * @param storeName
     * @return
     * @throws IOException
     */
    public static StreamingResponseBody readFileStreamContent(String path, String storeName) throws IOException {
        return readFileStreamContent(Paths.get(path), storeName);
    }

    /**
     * readFileStreamContent
     * 
     * @param path
     * @param storeName
     * @return
     * @throws IOException
     */
    public static StreamingResponseBody readFileStreamContent(Path path, String storeName) throws IOException {
        // Get file
        Path file = path.resolve(storeName);
        UrlResource urlResource = new UrlResource(file.toUri());

        return outputStream -> {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            InputStream inputStream = urlResource.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        };
    }

    /**
     * copy temp file to chat/task path
     * 
     * @param rootUploadPath
     * @param targetPath
     * @param targetFileName
     * @param userName
     * @param isChat
     * @throws IOException
     */
    public static void saveTempFile(String rootUploadPath,
            String targetPath,
            String targetFileName,
            String userName,
            boolean isChat) throws IOException {

        // Get target file
        Path tempPath = Paths.get(rootUploadPath, AppConstant.PATH_TEMP_PREFIX, userName);
        tempPath = tempPath.resolve(Paths.get(targetFileName)).normalize().toAbsolutePath();

        // Setting destination file
        String requestPath = isChat ? AppConstant.PATH_CHAT_PREFIX : AppConstant.PATH_TASK_PREFIX;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        File destFile = getTargetPath(rootUploadPath, requestPath, targetPath, timestamp)
                .resolve(Paths.get(targetFileName))
                .normalize()
                .toAbsolutePath()
                .toFile();

        // Copy file to destination path
        copyFile(tempPath.toFile(), destFile);

        // Delete temp path
        Files.delete(tempPath);
    }

    /**
     * copyFile
     * 
     * @param targetFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File targetFile, File destFile) throws IOException {
        Files.copy(targetFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Get target path file upload
     * 
     * @param rootUploadPath
     * @param fileClassify
     * @param targetPath
     * @param timestamp
     * @return
     */
    public static Path getTargetPath(String rootUploadPath, String fileClassify, String targetPath,
            Timestamp timestamp) {

        // Get target path file upload
        Path resultPath = Paths.get(rootUploadPath, fileClassify.toLowerCase(), targetPath);
        if (timestamp != null) {
            resultPath = Paths.get(rootUploadPath,
                    fileClassify,
                    targetPath,
                    String.valueOf(timestamp.toLocalDateTime().getYear()),
                    String.valueOf(timestamp.toLocalDateTime().getMonthValue()),
                    String.valueOf(timestamp.toLocalDateTime().getDayOfMonth()));
        }

        // Create directory if not exist
        if (!resultPath.toFile().exists()) {
            resultPath.toFile().mkdirs();
        }

        return resultPath;
    }

    /**
     * check image type file
     * 
     * @param file
     * @return
     */
    public static boolean isImage(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(IMAGE_TYPES).contains(fileExtension.trim().toLowerCase());
    }

    /**
     * Delete path
     * 
     * @param targetPath
     * @throws IOException
     */
    public static void deletePath(Path targetPath) throws IOException {

        File targetFolderPath = targetPath.toFile();
        if (targetFolderPath.exists()) {
            File[] files = targetFolderPath.listFiles();
            for (File file : files) {
                Files.delete(file.toPath());
            }
        }

        // Delete temp path
        Files.delete(targetPath);
    }
}
