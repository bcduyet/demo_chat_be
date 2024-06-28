package co.jp.xeex.chat.token;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The class to register the filters.
 * 
 * @author v_long
 */
@Configuration
public class JwtTokenConfig {    
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter(JwtTokenService jwtTokenService) {
        FilterRegistrationBean<JwtAuthenticationFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new JwtAuthenticationFilter(jwtTokenService));        
        return filter;
    }
}
