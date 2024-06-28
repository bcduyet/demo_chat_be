package co.jp.xeex.chat.token;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * JwtTokenService interface
 * @author
 */
public interface JwtTokenService {

    /**
     * Create the token
     * 
     * @param claims the dto to create the token
     * @return the token string
     */
    String createToken(TokenClaimData claims);

    /**
     * Validate the token
     * 
     * @param token the token to validate
     * @return true if the token is valid, false otherwise
     * @throws Exception
     */
    boolean validateToken(String token) throws BusinessException;

    /**
     * Get the subject from the token
     * 
     * @param token the token to get the subject from
     * @return the subject
     */
    String getSubject(String token);

    /**
     * Get the user role ids from the token
     * 
     * @param token the token to get the user role ids from
     * @return the user role ids
     */
    List<Long> getUserRoleIds(String token);

    /**
     * Get the expiration date from the token
     * 
     * @param token the token to get the expiration date from
     * @return the expiration date
     */
    Date getExpirationDate(String token);

    /**
     * Check if the token is expired
     * 
     * @param token the token to check
     * @return true if the token is expired, false otherwise
     */
    boolean isTokenExpired(String token);
   
    /**
     * Get the language from the token
     * @param token
     * @return the language code
     */
    String getLang(String token);
    /**
     * Get the claim dto data from the token
     * @param token the token to get the claim data from
     * @return the claim data object
     */
    TokenClaimData getClaimData(String token);

    /**
     * Get the username from the token
     * @param token the token to get the username from
     * @return the username
     */
    String getUsernameFromToken(String token);

    UserDetails getUserDetailsFromToken(String token);
}
