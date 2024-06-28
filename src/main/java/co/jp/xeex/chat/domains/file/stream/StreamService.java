package co.jp.xeex.chat.domains.file.stream;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * This service is used to stream media files to the client.
 * 
 * @author v_long
 */
public interface StreamService {
        /**
         * Loads the entire media file specified by the local media file path.
         *
         * @param localMediaFilePath The path of the local media file to be loaded.
         * @return A ResponseEntity containing the streaming response body for the
         *         loaded media file.
         * @throws BusinessException If there is a business exception during the
         *                            loading process.
         * @throws IOException        If an I/O error occurs during the loading process.
         */
        ResponseEntity<StreamingResponseBody> loadAlleMediaFile(String localMediaFilePath)
                        throws BusinessException, IOException;

        /**
         * Loads a partial section of the media file specified by the local media file
         * path, based on the provided range values.
         *
         * @param localMediaFilePath The path of the local media file to be loaded.
         * @param rangeValues        The range values specifying the section of the
         *                           media file to be loaded.
         * @return A ResponseEntity containing the streaming response body for the
         *         loaded media file section.
         * @throws BusinessException If there is a business exception during the
         *                            loading process.
         * @throws IOException        If an I/O error occurs during the loading process.
         */
        ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, String rangeValues)
                        throws BusinessException, IOException;

        /**
         * Loads a partial section of the media file specified by the local media file
         * path, based on the provided start and end positions.
         *
         * @param localMediaFilePath The path of the local media file to be loaded.
         * @param fileStartPos       The start position of the section to be loaded.
         * @param fileEndPos         The end position of the section to be loaded.
         * @return A ResponseEntity containing the streaming response body for the
         *         loaded media file section.
         * @throws BusinessException If there is a business exception during the
         *                            loading process.
         * @throws IOException        If an I/O error occurs during the loading process.
         */
        ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, long fileStartPos,
                        long fileEndPos) throws BusinessException, IOException;

}
