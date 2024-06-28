package co.jp.xeex.chat.token;

/**
 * The interface to define the Dto as token claim data.
 * 
 * @author v_long, q_thinh
 */
public interface TokenClaimData {
    String getUserId();

    String getUserName();

    String getFullName();

    String getEmail();

    String getLang();

    TokenType getTokenType();
}
