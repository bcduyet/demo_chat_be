package co.jp.xeex.chat.domains.chat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import co.jp.xeex.chat.token.TokenClaimData;

/**
 * Socket conf
 * 
 * @author v_long
 */
@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    private final SocketHandshakeInterceptor tokenHandshakeInterceptor;
    private static final int ONE_KB = 1024;
    /**
     * message size limit (in Kb)
     */
    @Value("${websocket.message-size-limit}")
    private int messageSizeLimit;

    /**
     * send buffer size limit (in Kb)
     */
    @Value("${websocket.send-buffer-size-limit}")
    private int sendBufferSizeLimit;

    /**
     * send time limit (in seconds)
     */
    @Value("${websocket.send-time-limit}")
    private int sendTimeLimit;

    public SocketBrokerConfig(SocketHandshakeInterceptor tokenHandshakeInterceptor) {
        this.tokenHandshakeInterceptor = tokenHandshakeInterceptor;

    }

    @SuppressWarnings("null")
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(ChatEndPoints.CHAT_ROOT_HOST)
                .setAllowedOriginPatterns("*")
                .addInterceptors(tokenHandshakeInterceptor)
                .withSockJS()
                .setSessionCookieNeeded(true);
    }

    @SuppressWarnings("null")
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // all api start with /chat/...
        registry.setApplicationDestinationPrefixes(ChatEndPoints.CHAT_ROOT_API);

        // enable simple broker for chat group, chat user, and chat all
        registry.enableSimpleBroker(
                ChatEndPoints.CHAT_GROUP_SUBSCRIBR,
                ChatEndPoints.CHAT_USER_SUBSCRIBR,
                ChatEndPoints.CHAT_ALL_SUBSCRIBR,
                ChatEndPoints.CHAT_PRIVATE_MARK_SUBSCRIBR);

        // enable user destination for subscribe to private messages
        registry.setUserDestinationPrefix(ChatEndPoints.CHAT_USER_SUBSCRIBR);
    }

    @SuppressWarnings("null")
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        /*
         * Add a factory that to decorate the handler used to process WebSocket messages
         * afterConnectionEstablished: hold the session of the user
         * this session will be used to close all sessions of a user when user logout
         */
        WebSocketHandlerDecoratorFactory factory = handler -> new WebSocketHandlerDecorator(handler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                // get the token data from the session (set in SocketHandshakeInterceptor)
                if (session.getAttributes().containsKey("token")) {
                    TokenClaimData claimData = (TokenClaimData) session.getAttributes().get("token");
                    // add the session to the list of sessions
                    SocketSessionStorage.addSession(claimData.getUserName(), session);
                }
                super.afterConnectionEstablished(session);
            }
        };
        registry.addDecoratorFactory(factory);
        //
        // some configuration for the transport
        // Set the maximum size of a message that can be received on the WebSocket.
        registry.setMessageSizeLimit(this.messageSizeLimit * ONE_KB); // MB
        // Set the maximum size of the send buffer for WebSocket messages.
        registry.setSendBufferSizeLimit(this.sendBufferSizeLimit * ONE_KB); // MB
        // Set the maximum time (in milliseconds) that the server will wait for a
        // WebSocket message to be sent.
        registry.setSendTimeLimit(this.sendTimeLimit * 1000); // ms
    }

}
