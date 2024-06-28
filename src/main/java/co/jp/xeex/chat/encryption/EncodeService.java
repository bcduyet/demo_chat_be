package co.jp.xeex.chat.encryption;

/**
 * Interface for encrypting and decrypting values.
 * @author v_long
 */
public interface EncodeService {
    /**
     * Encrypts the given value using the specified salt.
     *
     * @param textValue the value to be encrypted   
     * @return the encrypted value    
     */
    String encode(String textValue);

    /**
     * Matches the raw password with the encoded password.
     * @param rawPassword 
     * @param encodedPassword
     * @return true if the raw password matches the encoded password, false otherwise
     */
    boolean matches(String rawPassword, String encodedPassword);
}
