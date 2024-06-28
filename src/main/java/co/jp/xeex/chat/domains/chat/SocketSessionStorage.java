package co.jp.xeex.chat.domains.chat;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

/**
 * keep a list of websocket sessions<br>
 * This class is used to store all current user's WebSocket sessions
 * and to close all sessions of a user when he logs out.
 * 
 * @author v_long
 */
public class SocketSessionStorage {
    // key - username, value - List of user's sessions
    // why use List instead of Set? because a user can open multiple tabs?
    // because a user can open chat on multiple devices.
    private static Map<String, List<WebSocketSession>> sessions = new java.util.concurrent.ConcurrentHashMap<>();

    // singleton
    private SocketSessionStorage() {
    }

    /**
     * Add a session to the list of sessions
     * 
     * @param username
     * @param session
     */
    public static void addSession(String username, WebSocketSession session) {
        synchronized (sessions) {
            var userSessions = sessions.get(username);
            if (userSessions == null)
                userSessions = new ArrayList<>();
            userSessions.add(session);
            sessions.put(username, userSessions);
        }
    }

    /**
     * Remove a session from the list of sessions
     * 
     * @param username username
     */
    public static void closeSessions(String username) throws IOException {
        synchronized (sessions) {
            var userSessions = sessions.get(username);
            if (userSessions != null) {
                for (var session : userSessions) {
                    session.close(CloseStatus.POLICY_VIOLATION);
                }
                sessions.remove(username);
            }
        }
    }
}
