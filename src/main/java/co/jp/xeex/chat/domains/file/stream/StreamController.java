package co.jp.xeex.chat.domains.file.stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;

import co.jp.xeex.chat.common.RestApiEndPoints;
import co.jp.xeex.chat.exception.BusinessException;
import lombok.extern.log4j.Log4j;

/**
 * Rest api endpoint for streaming video/audio files
 * 
 * @author v_long
 */
@Log4j
@RestController
public class StreamController {
    private final StreamService streamService;

    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    /**
     * This variable needs to be set in the environment variable<br>
     * The server's root directory to store uploaded media files
     */
    private static final String MEDIA_ROOT_PATH = "./src/main/resources/chat/";


    //API: YOU CAN USE ANY

    /**
     * Api stream medial file, use RequestBody
     * 
     * @param requestBody json contains file information to stream
     */
    @GetMapping(RestApiEndPoints.MEDIAL_PATH)
    public ResponseEntity<StreamingResponseBody> stream(@RequestBody StreamRequest requestBody,
            @RequestHeader(value = "Range", required = false) String rangeHeader)
            throws BusinessException, IOException {
        String filePathString = (MEDIA_ROOT_PATH + requestBody.filePath).replace("//", "/");// case with or without / in
                                                                                            // front
        return streamService.loadPartialMediaFile(filePathString, rangeHeader);

    }

    /**
     * Api stream medial file, use PathVariable
     * @param filePath file path to stream 
     */
    @GetMapping(RestApiEndPoints.MEDIAL_VARIABLE_PATH)
    public ResponseEntity<StreamingResponseBody> stream(@PathVariable("filePath") String filePath,
            @RequestHeader(value = "Range", required = false) String rangeHeader)
            throws BusinessException, IOException {
        String filePathString = (MEDIA_ROOT_PATH + filePath).replace("//", "/");
        return streamService.loadPartialMediaFile(filePathString, rangeHeader);

    }
//
    /**
     * Separate error handling for this RestController
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StreamingResponseBody> handleException(Exception e) {
        log.debug("Error " + e.getMessage());
        return null;
    }

    /**
     * Error handling specific to this RestController
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<StreamingResponseBody> handleBussinessException(BusinessException e) {
        log.debug("Error " + e.getMessage());
        return null;
    }
}
