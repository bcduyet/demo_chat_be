package co.jp.xeex.chat;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The application context provider.<br>
 * User to get the application context from the static method.
 * This usefull to get the bean from the application context when the bean is cannot be autowired.
 * @author v_long
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(@SuppressWarnings("null") ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

}
