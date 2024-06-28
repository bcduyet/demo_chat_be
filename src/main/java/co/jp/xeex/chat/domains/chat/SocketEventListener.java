package co.jp.xeex.chat.domains.chat;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.log4j.Log4j;

/**
 * SocketEventListener for handle web socket connection and disconnection
 * 
 * @author v_long
 */
@Component
@Log4j
public class SocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        log.info("Received a new web socket disconnection");
    }
}
