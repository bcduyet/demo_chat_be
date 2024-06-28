package co.jp.xeex.chat.util;

import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class EnvironmentUtil {

    // constant env key
    private static final String HTTP = "http";
    private static final String ENV_SERVER_ADDRESS_KEY = "server.address";
    private static final String ENV_SERVER_PORT_KEY = "server.port";

    // DI
    private Environment env;

    public EnvironmentUtil(Environment env) {
        this.env = env;
    }

    /**
     * Get value properties with key
     * 
     * @param configKey
     * @return
     */
    public String getConfigValue(String configKey) {
        return env.getProperty(configKey);
    }

    /**
     * Get domain host
     * 
     * @return
     */
    public String getDomain() {
        return String.format("%s://%s:%s",
                HTTP,
                getConfigValue(ENV_SERVER_ADDRESS_KEY),
                getConfigValue(ENV_SERVER_PORT_KEY));
    }
}
