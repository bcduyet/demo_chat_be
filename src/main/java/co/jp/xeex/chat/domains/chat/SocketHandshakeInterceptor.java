package co.jp.xeex.chat.domains.chat;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import co.jp.xeex.chat.exception.BusinessException;
import co.jp.xeex.chat.token.JwtTokenService;
import co.jp.xeex.chat.token.TokenClaimData;
import co.jp.xeex.chat.token.TokenType;
import lombok.extern.log4j.Log4j;

/**
 * TokenHandshakeInterceptor for token authentication apply with socket
 * 
 * @author v_long
 */
@Log4j
@Component
public class SocketHandshakeInterceptor implements HandshakeInterceptor {
    private static final String CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN = "CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN";
    private TokenClaimData tokenData;
    private final JwtTokenService jwtTokenService;

    public SocketHandshakeInterceptor(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @SuppressWarnings("null")
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
        /*
         * remark
         * The WebSocket protocol does not support custom headers during Handshake.
         * (This is a limitation of the WebSocket protocol)
         * The client side cannot send headers during Handshake.
         * This means that tokens cannot be sent in the headers of WebSocket requests.
         * but must send the token in the query string.
         */
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        String token = servletRequest.getServletRequest().getParameter("token");
        // check token
        if (jwtTokenService.validateToken(token)) {
            // store the user in the attributes for later usage
            // (e.g: SocketEventListener will use this to send messages to all current
            // user's group/friends)
            this.tokenData = jwtTokenService.getClaimData(token);
            if (this.tokenData.getTokenType() == TokenType.REFRESH) {
                throw new BusinessException(CONTROLLER_BASE_NOT_ALLOW_REFRESH_TOKEN, this.tokenData.getLang());
            }

            attributes.put("token", tokenData);
            log.info("Handshake successful for user [" + this.tokenData.getUserName() + "]");
            return true;
        }
        // if token is invalid, throw an exception
        String message = "Token is invalid";
        log.error("TokenHandshakeInterceptor: Handshake failed [" + message + "]");
        throw new Exception(message);
    }

    @SuppressWarnings("null")
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception exception) {
        if (exception == null) {
            // Report a successful handshake for all user's friends?
            log.info("Handshake successful for user [" + this.tokenData.getUserName() + "]");
        } else {
            log.error("TokenHandshakeInterceptor: Handshake failed [" + exception.getMessage() + "]");
        }
    }

}
