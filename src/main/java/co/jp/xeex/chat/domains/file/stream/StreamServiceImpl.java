package co.jp.xeex.chat.domains.file.stream;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import co.jp.xeex.chat.exception.BusinessException;
import lombok.extern.log4j.Log4j;

/**
 * @author v_long
 */
@Service
@Log4j
public class StreamServiceImpl implements StreamService {

    private static final String STREAM_ERROR_FILE_NOT_FOUND = "STREAM_ERROR_FILE_NOT_FOUND";

    @Override
    public ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, long fileStartPos,
            long fileEndPos) throws BusinessException, IOException {
        StreamingResponseBody responseStream;
        Path filePath = Paths.get(localMediaFilePath);
        if (!filePath.toFile().exists()) {
            log.error("The media file does not exist.");
            throw new BusinessException(STREAM_ERROR_FILE_NOT_FOUND);
        }

        // get the file size
        long fileSize = Files.size(filePath);

        // when: is the first time we are reading the file
        if (fileStartPos < 0L) {
            fileStartPos = 0L;
        }
        // when is the last time we are reading the file
        if (fileSize > 0L) {
            if (fileStartPos >= fileSize) {
                fileStartPos = fileSize - 1L;
            }
            if (fileEndPos >= fileSize) {
                fileEndPos = fileSize - 1L;
            }
        } else {
            fileStartPos = 0L;
            fileEndPos = 0L;
        }

        byte[] buffer = new byte[1024];
        String mimeType = Files.probeContentType(filePath);
        // the header for the response. it will be used for the next request
        final HttpHeaders responseHeaders = new HttpHeaders();
        String contentLength = String.valueOf((fileEndPos - fileStartPos) + 1);
        responseHeaders.add("Content-Type", mimeType);
        responseHeaders.add("Content-Length", contentLength);
        responseHeaders.add("Accept-Ranges", "bytes");
        responseHeaders.add("Content-Range", String.format("bytes %d-%d/%d", fileStartPos, fileEndPos, fileSize));

        final long fileStartPos2 = fileStartPos;
        final long fileEndPos2 = fileEndPos;
        responseStream = os -> {
            try (RandomAccessFile file = new RandomAccessFile(localMediaFilePath, "r")) {
                long pos = fileStartPos2;
                file.seek(pos);
                while (pos < fileEndPos2) {
                    file.read(buffer);
                    os.write(buffer);
                    pos += buffer.length;
                }
                os.flush();
            } catch (Exception e) {
            } finally {
                try {
                    os.close();
                } catch (IOException e) {
                    log.debug("Error closing the output stream :" + e.getMessage());
                }
            }
        };

        return new ResponseEntity<StreamingResponseBody>(responseStream, responseHeaders, HttpStatus.PARTIAL_CONTENT);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> loadAlleMediaFile(String localMediaFilePath)
            throws BusinessException, IOException {
        Path filePath = Paths.get(localMediaFilePath);
        if (!filePath.toFile().exists()) {
            log.error("The media file does not exist.");
            throw new BusinessException(STREAM_ERROR_FILE_NOT_FOUND);
        }

        long fileSize = Files.size(filePath);
        long endPos = 0;
        if (fileSize > 0L) {
            endPos = fileSize - 1;
        } else {
            endPos = 0L;
        }
        return loadPartialMediaFile(localMediaFilePath, 0, endPos);
    }

    @Override
    public ResponseEntity<StreamingResponseBody> loadPartialMediaFile(String localMediaFilePath, String rangeValues)
            throws BusinessException, IOException {
        if (!StringUtils.hasText(rangeValues)) {
            log.debug("Read all media file content.");
            return loadAlleMediaFile(localMediaFilePath);
        } else {
            long rangeStart = 0L;
            long rangeEnd = 0L;

            if (!StringUtils.hasText(localMediaFilePath)) {
                log.error("The full path to the media file is NULL or empty.");
                throw new BusinessException(STREAM_ERROR_FILE_NOT_FOUND);
            }

            Path filePath = Paths.get(localMediaFilePath);
            if (!filePath.toFile().exists()) {
                log.error("The media file does not exist.");
                throw new BusinessException(STREAM_ERROR_FILE_NOT_FOUND);
            }

            long fileSize = Files.size(filePath);
            int dashPos = rangeValues.indexOf("-");
            if (dashPos > 0 && dashPos <= (rangeValues.length() - 1)) {
                String[] rangesArr = rangeValues.split("-");

                if (rangesArr != null && rangesArr.length > 0) {
                    log.debug("ArraySize: " + rangesArr.length);
                    if (StringUtils.hasText(rangesArr[0])) {
                        log.debug("Rang values[0]: [" + rangesArr[0] + "]");
                        String valToParse = numericStringValue(rangesArr[0]);
                        rangeStart = safeParseStringValuetoLong(valToParse, 0L);
                    } else {
                        rangeStart = 0L;
                    }

                    if (rangesArr.length > 1) {
                        log.debug("Rang values[1]: [" + rangesArr[1] + "]");
                        String valToParse = numericStringValue(rangesArr[1]);
                        rangeEnd = safeParseStringValuetoLong(valToParse, 0L);
                    } else {
                        if (fileSize > 0) {
                            rangeEnd = fileSize - 1L;
                        } else {
                            rangeEnd = 0L;
                        }
                    }
                }
            }

            if (rangeEnd == 0L && fileSize > 0L) {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }

            log.debug(String.format("Parsed Range Values: [%d] - [%d]", rangeStart, rangeEnd));

            return loadPartialMediaFile(localMediaFilePath, rangeStart, rangeEnd);
        }
    }

    // PRIVATE
    /**
     * Safely parse the specified string value to a long integer value.
     * If the string value is NULL or empty, the default value is returned.
     * 
     * @param valToParse the string value to parse
     * @param defaultVal the default value to return if the string value is NULL or
     *                   empty
     * @return the value of the string value as a long integer
     */
    private long safeParseStringValuetoLong(String valToParse, long defaultVal) {
        long retVal = defaultVal;
        if (StringUtils.hasText(valToParse)) {
            try {
                retVal = Long.parseLong(valToParse);
            } catch (NumberFormatException ex) {
                retVal = defaultVal;
            }
        }

        return retVal;
    }

    /**
     * Parse the specified string value to a numeric string value.
     * 
     * @param origVal the string value to parse
     * @return the numeric string value
     */
    private String numericStringValue(String origVal) {
        String retVal = "";
        if (StringUtils.hasText(origVal)) {
            retVal = origVal.replaceAll("[^0-9]", "");
        }
        return retVal;
    }
}
