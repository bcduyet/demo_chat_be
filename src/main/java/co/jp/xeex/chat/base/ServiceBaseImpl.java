package co.jp.xeex.chat.base;

import co.jp.xeex.chat.exception.BusinessException;

public abstract class ServiceBaseImpl<I extends RequestBase, O>
        implements ServiceBase<I, O> {

    /**
     * This method is called before processing the request.<br>
     * You can override this method to initialize the process.
     * 
     * @param request
     */
    protected void innitProcess(I request) {
        // do nothing
    }

    /**
     * This method is called after processing the request.<br>
     * You can override this method to do something after processing.
     * 
     * @param in
     * @param out
     */
    protected void afterProcess(I in, O out) {
        // do nothing
    }

    /**
     * This method is the main processes in executing workflow.<br>
     * Execute the business logic to make the business OutputData as a response data
     * object.
     * 
     * @param in The request object.
     * @return OutputData object
     * @throws Exception
     */
    protected abstract O processRequest(I in) throws BusinessException;

    @Override
    public O execute(I in) throws BusinessException {
        try {
            // innit process
            this.innitProcess(in);

            // Execute process logic
            O out = this.processRequest(in);

            // after process
            this.afterProcess(in, out);

            return out;
        } catch (BusinessException e) {
            throw e;
        }
    }

}
