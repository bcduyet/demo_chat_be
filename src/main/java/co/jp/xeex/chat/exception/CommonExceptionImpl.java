package co.jp.xeex.chat.exception;

import co.jp.xeex.chat.ApplicationContextProvider;
import co.jp.xeex.chat.lang.resource.MessageResourceConfig;
import co.jp.xeex.chat.lang.resource.ResourceMessageService;
import co.jp.xeex.chat.lang.resource.ResourceMessageServiceImpl;

/**
 * Common exception implementation.
 * 
 * @author v_long, q_thinh
 */
@SuppressWarnings({ "squid:S1165", "squid:S1948" })
class CommonExceptionImpl extends RuntimeException implements CommonException {
    private static final long serialVersionUID = 1L;
    private String msgId = null;
    private String lang = null;
    private Object data = null;
    private String message = null;
    private String[] msgParams = null;

    private ResourceMessageService messageService;

    private void init() {
        MessageResourceConfig messageResourceConfig = ApplicationContextProvider.getApplicationContext()
                .getBean(MessageResourceConfig.class);
        this.messageService = new ResourceMessageServiceImpl(messageResourceConfig);
    }

    public CommonExceptionImpl(String key) {
        super();
        init();
        this.msgId = key;
        this.lang = "en";// default language
        this.message = messageService.getMessage(this.getMessageId(), this.lang);
    }

    public CommonExceptionImpl(String key, String lang) {
        super();
        init();
        this.msgId = key;
        this.lang = lang;
        this.message = messageService.getMessage(this.getMessageId(), this.lang);
    }

    public CommonExceptionImpl(String key, String[] msgParams, String lang) {
        super();
        init();
        this.msgId = key;
        this.lang = lang;
        this.msgParams = msgParams;
        this.message = messageService.getMessage(this.getMessageId(), this.lang);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getMessageId() {
        return msgId;
    }

    @Override
    public Object getErrorData() {
        return data;
    }

    @Override
    public void setErrorData(Object data) {
        this.data = data;
    }

    @Override
    public String[] getMsgParams() {
        return msgParams;
    }

    @Override
    public void setMsgParams(String[] msgParams) {
        this.msgParams = msgParams;
    }

    @Override
    public String getLang() {
        return lang;
    }

}
