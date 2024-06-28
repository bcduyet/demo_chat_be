package co.jp.xeex.chat.base;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * This interface defines the base service for business logic processing requests.
 * 
 * @param <I>  The request type.
 * 
 * @author v_long
 */
public interface ServiceBase<I extends RequestBase, O>  {   
    /**
     * This method execute the the workflow: <br>
     * 1. Validate the input (request data). <br>
     * 2. Execute the innitProcess() method to initialize the process. <br>
     * 3. Execute the processRequest() method to make the business OutputData as a response data object. <br>
     * 4. Execute the afterProcess() method to do something after processing. <br>
     * 5. Return a ResponseEntity with body has a OutputData object to response to the client. <br>
     * @param InRequest The input request object.
     * @return ResponseDto as data object for EntityResponse response.
     */
    O execute(I request) throws BusinessException;
}
