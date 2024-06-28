package co.jp.xeex.chat.encryption;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The service to encode the password.
 * @author v_long 
 */
@Service
public class EncodeServiceImpl implements EncodeService {

    private final PasswordEncoder encodeder = new BCryptPasswordEncoder();

    @Override
    public String encode(String value) {
       return encodeder.encode(value);
    }
    
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encodeder.matches(rawPassword, encodedPassword);
    }
    
}
