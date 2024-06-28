package co.jp.xeex.chat.domains.chat;

/**
 * This class is a enum class for chat action.<br>
 * (LOGIN, LOGOUT, CHAT, JOIN, LEAVE, TYPING, REPPLY, REACTION, SEND_FILE,
 * ADD_NEW_GROUP, DELETE_GROUP, EDIT_GROUP, ADD_FRIEND, DELETE_FRIEND,
 * CREATE_TASK_MESSAGE, ERROR)
 * <br>
 * 
 * @author v_long
 */
public enum ChatAction {
    /*
     * CHAT: when a user send a message to a group or another user
     */
    CHAT,
    /*
     * EDIT_CHAT: when a user edit a message
     */
    EDIT_CHAT,
    /*
     * DELETE_CHAT: when a user delete a message
     */
    DELETE_CHAT,
    /*
     * EDIT_DELETE_CHAT: when a user delete a message
     * exist repply message
     */
    EDIT_DELETE_CHAT,
    /*
     * EDIT_DELETE_CHAT: when a user delete a message
     * exist repply message
     */
    DELETE_FILE_CHAT,
    /*
     * JOIN: when a user join a chat group
     */
    JOIN,
    /*
     * LEAVE: when a user leave a chat group
     */
    LEAVE,
    /*
     * TYPING: when a user is typing a message
     */
    TYPING,
    /*
     * REPPLY: when a user repply to a message
     */
    REPPLY,
    /**
     * REACTION: when a user react to a message
     * (like, love, laugh, sad, angry, wow, etc.)
     */
    REACTION,
    /**
     * MENTION: when a user Mention user
     */
    MENTION,
    /**
     * SEND_FILE: when a user send a file to a group or another user
     */
    SEND_FILE,
    /**
     * ADD_NEW_CHAT_GROUP: when a user create a new group
     * Use this action to create a new group for all users in group<br>
     * or when a user join a group, the group will be added to current user
     */
    ADD_NEW_CHAT_GROUP,
    /**
     * DELETE_CHAT_GROUP: when a user delete a group, all users in group will be
     * removed<br>
     * or when a user leave/remove from a group, the group will be deleted from
     * current user
     */
    DELETE_CHAT_GROUP,
    /**
     * EDIT_CHAT_GROUP: when a user edit a group info, <br>
     * the group info will be updated for all users's clients in group<br>
     * (group name, group image, group description, etc.).
     */
    EDIT_CHAT_GROUP,
    /**
     * ADD_FRIEND: when a user add friend
     */
    ADD_FRIEND,
    /**
     * DELETE_FRIEND: when a user delete friend
     */
    DELETE_FRIEND,
    /**
     * CREATE_TASK_MESSAGE: when a user create task from message
     */
    CREATE_TASK_MESSAGE,
    /**
     * DELETE_TASK_MESSAGE: when a user delete task from message
     */
    DELETE_TASK_MESSAGE,
    /**
     * ERROR: when an error occurs
     */
    ERROR
}