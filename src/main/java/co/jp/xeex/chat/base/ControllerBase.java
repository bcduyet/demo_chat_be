package co.jp.xeex.chat.base;

import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.exception.CommonException;
import co.jp.xeex.chat.lang.resource.ResourceMessageItem;
import co.jp.xeex.chat.lang.resource.ResourceMessageLevels;
import co.jp.xeex.chat.lang.resource.ResourceMessageTypes;
import co.jp.xeex.chat.token.TokenClaimData;
import co.jp.xeex.chat.token.TokenType;
import co.jp.xeex.chat.validation.DtoValidateService;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as a base class for REST controllers.<br>
 * It provides common methods that most REST controllers will use.<br>
 * When creating a new REST controller, it is recommended to extend this
 * class.<br>
 * Note: There are still many methods that have not been implemented yet...
 * 
 * @author v_long
 */
@Log4j
@ControllerAdvice
public abstract class ControllerBase<R extends RequestBase> {
    private static final String CONTROLLER_BASE_OUTPUT_NULL = "CONTROLLER_BASE_OUTPUT_NULL";
    private static final String CONTROLLER_BASE_INPUT_INVALID = "CONTROLLER_BASE_INPUT_INVALID";
    private static final String SYSTEM_ERR_GENERAL = "SYSTEM_ERR_GENERAL";
    private static final String CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN = "CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN";

    @Autowired
    private DtoValidateService validation;
    private String lang = "en";// default language
    // share for chill class
    protected String currentUser;
    protected TokenClaimData claimData;

    /**
     * Default: FALSE (donot allow)<br>
     * In most cases, the API will not allow the use of refresh tokens to access
     * API. So this value will be true to throw the error when client use refresh
     * tokens to access api. <br>
     * However, in the case of refreshToken api, this value is false allow access
     * api
     */
    protected boolean getAllowRefreshToken() {
        return false;
    }

    /**
     * Default: FALSE (donot allow)<br>
     * allow request with null request.
     * Default value is true.
     * If set false, preProcessRequest() will throw error when request is null.
     */
    protected boolean getAllowNullRequests() {
        return false;
    }

    /**
     * Template for Create a ResponseEntity with body has a ResponseDto object.
     * 
     * @param request The request data object.
     * @return ResponseEntity with body has a ResponseDto object.
     */
    public abstract ResponseEntity<?> restApi(@RequestBody R request) throws BusinessException;

    public String getCurrentLang() {
        return this.lang;
    }

    public String getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Pre-process the request before executing the business logic.
     * 
     * @param dto The request data object.
     * @throws BusinessException
     */
    protected void preProcessRequest(R dto) throws BusinessException {
        // check null request
        if (dto == null) {
            if (!this.getAllowNullRequests()) {
                throw new BusinessException(CONTROLLER_BASE_OUTPUT_NULL, this.lang);
            }
            return;// do nothing
        }
        //
        String json = new Gson().toJson(dto);
        log.info("preProcessRequest: " + json);
        // keep origin dot's values
        this.lang = dto.lang;
        this.currentUser = dto.requestBy;
        // get authentication is created by JwtAuthenticationFilter
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        this.claimData = null != authentication ? (TokenClaimData) authentication.getCredentials() : null;
        // Prioritize the use of values ​​in tokens
        if (dto != null && null != claimData) {
            // get token info and set to request body for next chain
            String requestBy = claimData.getUserName();
            this.currentUser = requestBy;
            this.lang = dto.lang == null || dto.lang.isEmpty() ? this.claimData.getLang() : dto.lang;
            if (null != requestBy && !requestBy.isEmpty()) {
                dto.requestBy = requestBy;
            }

            if (null != this.lang && !this.lang.isEmpty()) {
                dto.lang = this.lang;
            }
            //
            if (!this.getAllowRefreshToken() && this.claimData.getTokenType() == TokenType.REFRESH) {
                throw new BusinessException(CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN, this.lang);
            }
        }
        //
        log.info("Validate request: " + json);
        this.validateRequest(dto);

        //
        log.info("Check authorization: " + json);
        this.checkAuthoriton(dto);
    }

    /**
     * Create a ResponseEntity with body has a ResponseDto object for success
     * 
     * @param resultData The proccess result data object.
     * @return ResponseEntity with body has a ResponseDto object.
     */
    protected final ResponseEntity<?> createSuccessResponse(Object resultData) {
        return this.createResponse(HttpStatus.OK, "PROCESS_SUCCESS", "Success", resultData, null);
    }

    /**
     * Create a ResponseEntity with body is null (for fail response)
     * 
     * @return ResponseEntity with body has a ResponseDto object.
     */
    protected final ResponseEntity<?> createNullFailResponse() {
        CommonException e = new BusinessException(CONTROLLER_BASE_OUTPUT_NULL, this.lang);
        return this.createErrorResponse(e);
    }

    /**
     * Create a ResponseEntity with exception message (for fail response)
     * 
     * @param e
     * @return
     */
    protected final ResponseEntity<?> createErrorResponse(CommonException e) {
        return this.createResponse(HttpStatus.BAD_REQUEST, e.getMessageId(), e.getMessage(), null, e.getErrorData());

    }

    /**
     * Create a ResponseEntity with body has a ResponseDto object.
     * 
     * @param status     HttpStatus value
     * @param mesageId   The message id of the response (for i18n)
     * @param message    The message of the response (for i18n)
     * @param resultData The data object stored in the response.data
     * @return
     */
    private final ResponseEntity<?> createResponse(HttpStatus status, String mesageId, String message,
            Object resultData, Object errorList) {
        ResponseBody response = new ResponseBody();
        response.setStatus(status.value());
        response.setMessageId(mesageId);
        response.setData(resultData);
        response.setErrors(errorList);
        response.setHasError(status != HttpStatus.OK);
        response.setMessage(message);
        return new ResponseEntity<>(response, status);
    }

    // Validate request
    private void validateRequest(R request) throws BusinessException {
        if (null == request) {
            return;
        }
        List<ResourceMessageItem> errors = this.validation.getErrors(request, request.lang);
        if (null != errors) {
            BusinessException e = new BusinessException(CONTROLLER_BASE_INPUT_INVALID, request.lang);
            e.setErrorData(errors);
            throw e;
        }
    }

    private void checkAuthoriton(R request) {
        // implement here
        log.info("Check authorization: " + request);
    }

    // ExceptionHandler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        try {
            log.error("Exception: " + e.getMessage());
            // create details for error
            List<ResourceMessageItem> errorList = new ArrayList<>();
            ResourceMessageItem err = new ResourceMessageItem();
            err.setMessage(e.getMessage());
            err.setLevel(ResourceMessageLevels.ERROR);
            err.setType(ResourceMessageTypes.SYSTEM);
            err.setMessageId(SYSTEM_ERR_GENERAL);
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace.length > 0) {
                StackTraceElement element = stackTrace[0];
                String className = element.getClassName();
                err.setSourceName(className);
            }
            log.error(err.getMessage());
            errorList.add(err);
            // wrap exception
            BusinessException ex = new BusinessException(SYSTEM_ERR_GENERAL, this.lang);
            ex.setErrorData(errorList);
            return this.createErrorResponse(ex);

        } catch (Exception ex1) {
            log.debug(ex1.getMessage());// not importance
            return null;
        }
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBussinessException(BusinessException e) {
        try {
            String message = String.format(e.getMessage(), (Object[]) e.getMsgParams());
            log.error("BussinessException: " + message);
            if (e.getErrorData() == null) {
                List<ResourceMessageItem> errorList = new ArrayList<>();
                ResourceMessageItem err = new ResourceMessageItem();
                err.setMessage(message);
                err.setMessageId(e.getMessageId());
                err.setLevel(ResourceMessageLevels.ERROR);
                err.setType(ResourceMessageTypes.BUSINESS);
                StackTraceElement[] stackTrace = e.getStackTrace();
                if (stackTrace.length > 0) {
                    StackTraceElement element = stackTrace[0];
                    String className = element.getClassName();
                    err.setSourceName(className);
                }
                errorList.add(err);
                e.setErrorData(errorList);
            }
            return this.createErrorResponse(e);
        } catch (Exception ex) {
            log.debug(ex.getMessage());// not importance
            return null;
        }
    }
}
