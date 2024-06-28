package co.jp.xeex.chat.domains.file.dto;

import co.jp.xeex.chat.domains.file.enums.FileType;
import lombok.Data;

/**
 * FileDto
 * 
 * @author q_thinh
 */
@Data
public class FileDto {
    /**
     * Represents the type of the file.
     */
    private FileType fileType;
    /**
     * The URL for downloading the file.
     */
    private String downloadUrl;
    /**
     * The URL of the image associated with the file.
     */
    private String imageUrl;
    /**
     * The original name of the file.
     */
    private String originName;
    /**
     * The name of the file as stored in the system.
     */
    private String storeName;
    /**
     * The employee code of the user who uploaded the file.
     */
    private String empCd;
}
