package co.jp.xeex.chat.domains.chat;

/**
 * This class contains the endpoints for the chat websocket.<br>
 * 
 * @author v_long
 */
public class ChatEndPoints {
    /**
     * The root host of the chat websocket<br>
     * pattern: https://{host}:{port}/{CHAT_ROOT_HOST}<br>
     * e.g. https://localhost:8080/ws
     */
    public static final String CHAT_ROOT_HOST = "ws";

    /**
     * ApplicationDestinationPrefixes for the chat websocket<br>
     * pattern: https://{host}:{port}/{CHAT_ROOT_HOST}/{CHAT_ROOT_API}<br>
     * e.g. https://localhost:8080/ws/{CHAT_ROOT_API}
     */
    public static final String CHAT_ROOT_API = "/chat";

    /**
     * The endpoint for client send the group chat message<br>
     * pattern: /{CHAT_GROUP_API}
     * e.g. e.g. stompClient.send('/group/chat',JSON.stringify(chatMessage))
     */
    public static final String CHAT_GROUP_API = "/group/chat";
    /**
     * The endpoint for client send the user chat message<br>
     * pattern: /{CHAT_USER_API}
     * e.g. stompClient.send('/user/chat',JSON.stringify(chatMessage))
     */
    public static final String CHAT_USER_API = "/user/chat";
    /**
     * The endpoint for client send the public chat message to public group<br>
     * pattern: /{CHAT_ALL_API}
     * e.g. stompClient.send('/all/chat',JSON.stringify(chatMessage))
     */
    public static final String CHAT_ALL_API = "/all/chat";

    /**
     * The endpoint for client receive the public chat message<br>
     * broadcast/subscribe with: /{CHAT_ALL_SUBSCRIBR}
     * e.g: stompClient.subscribe('/all/subscribe',...)
     */
    public static final String CHAT_ALL_SUBSCRIBR = "/all/subscribe";
    /**
     * broadcast url for chat group-to-group<br>
     * The endpoint for client receive the group chat message<br>
     * pattern: /{CHAT_GROUP_SUBSCRIBR}/{groupId}/{CHAT_PRIVATE_MARK_SUBSCRIBR}<br>
     * e.g: stompClient.subscribe('/group/{groupId}/private',...)
     */
    public static final String CHAT_GROUP_SUBSCRIBR = "/group";

    /**
     * broadcast url for chat user-to-user<br>
     * The endpoint for client receive the user chat message<br>
     * pattern: /{CHAT_USER_SUBSCRIBR}/{userName}/{CHAT_USER_DESTINATION_PREFIX}
     * e.g: stompClient.subscribe('/user/{userId}/private',...)
     */
    public static final String CHAT_USER_SUBSCRIBR = "/user";
    /**
     * Suffix of private message subscribers (group, user)<br>
     * /private
     */
    public static final String CHAT_PRIVATE_MARK_SUBSCRIBR = "/private";// for private message mark
}
