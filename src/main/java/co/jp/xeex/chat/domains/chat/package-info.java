package co.jp.xeex.chat.domains.chat;
/**
 * The main package performs chatting between users, groups, and public
 * --------------------------------------------------------------
 * Classes:
 * --------------------------------------------------------------
 * 
 * 1. ChatEndPoints: Contains the definition of chat host/endpoint uri
 * configurations
 * websockets
 *
 * 2. ChatAction: Enum defines chat message classification
 * (LOGIN, LOGOUT, CHAT, JOIN, LEAVE, TYPING, REPPLY, REACTION,...)
 * When we need to adjust, add or remove chat message categories, just edit this
 * enum
 *
 * 3. ChatMessageDto: Model for chat messages sent from client/server
 * After receiving chat message from client,
 * the server will process and send back to other clients the same structure
 *
 * 4. ChatMessage: Entity contains chat message information stored in the
 * database
 *
 * 5. ChatMessageRepository: Interface contains methods for querying the
 * database
 * ChatMessage
 *
 * 6. ChatMessageService: Interface Service handles/processing related requests
 * ChatMessage.
 *
 * 7. ChatMessageServiceImpl: Implement for ChatMessageService
 * This class is the main class that processes chat messages,
 * such as: processes chat messages, saves chat messages to the database, and
 * broadcasts chat messages to other clients
 *
 * 8. PeopleChatController: Controller acts as an endpoint
 * Receive chat messages from clients (derived from people-people,
 * people-group, people-public)
 * After processing is complete, return the results to the client
 *
 * 9. WebSocketBrokerConfig: Websocket configuration
 *
 * 10. SocketEventListener: implements the @EventListener to handle events
 * connect, disconnect socket
 *
 * 11. SocketHandshakeInterceptor: implements HandshakeInterceptor to handle
 * checks
 * Check token when handshake
 */