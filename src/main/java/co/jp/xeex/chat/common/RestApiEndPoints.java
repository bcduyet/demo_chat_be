package co.jp.xeex.chat.common;

/**
 * This class contains the endpoints of the all REST API.<br>
 * (Please add the endpoints of the REST API here.)
 */
@SuppressWarnings({ "squid:S1118" })
public class RestApiEndPoints {
    /** Comment explaining the api */
    public static final String USE_CASE = "/module/usecase";
    // auth
    /**
     * Login api. Success return token. <br>
     */
    public static final String LOGIN = "/auth/login";
    /**
     * Change password api. <br>
     */
    public static final String CHANGE_PWD = "/auth/change/pwd";
    /**
     * Logout api. reset the login status. <br>
     */
    public static final String LOGOUT = "/auth/logout";
    // admin
    /**
     * Find user list api. <br>
     */
    public static final String QUERY_USER = "/admin/search/user";
    /**
     * Create new of update user api. <br>
     */
    public static final String SAVE_USER = "/admin/save/user";
    /**
     * Delete a list of users api. example {"userIds": [1, 2, 3, 4, 5]}<br>
     */
    public static final String DELETE_USER = "/admin/delete/user";
    // lang
    /**
     * Get message resourrce api. <br>
     * Based on the language code, return the message resource. <br>
     */
    public static final String LANG_MSG = "/lang/resource";
    /**
     * Save chat group<br>
     */
    public static final String SAVE_GROUP = "/chatmngr/group/save";
    /**
     * Search chat group<br>
     */
    public static final String SEARCH_GROUP = "/chatmngr/group/search";
    /**
     * Get all chat group and chat friend<br>
     */
    public static final String GET_ALL_GROUP_FRIEND = "/chatmngr/groupfriend/getall";
    /**
     * Add friend<br>
     */
    public static final String ADD_FRIEND = "/chatmngr/friend/add";
    /**
     * Search friend<br>
     */
    public static final String SEARCH_FRIEND = "/chatmngr/friend/search";
    /**
     * Get none friend member<br>
     */
    public static final String GET_NONE_FRIEND_MEMBER = "/chatmngr/friend/getnone";
    /**
     * Delete friend<br>
     */
    public static final String DELETE_FRIEND = "/chatmngr/friend/delete";
    /**
     * Save message<br>
     */
    public static final String SAVE_MESSAGE = "/chatmngr/msg/save";
    /**
     * Upload file<br>
     */
    public static final String UPLOAD_FILE = "/file/upload";
    /**
     * Download file<br>
     */
    public static final String DOWNLOAD_FILE = "/file/{fileClassify}/{storeName}";
    /**
     * Delete file<br>
     */
    public static final String DELETE_FILE = "/file/delete";
    /**
     * Search dept<br>
     */
    public static final String SEARCH_DEPT = "/chatmngr/dept/search";
    /**
     * Get member in dept<br>
     */
    public static final String GET_DEPT_MEMBER = "/chatmngr/dept/getmbr";
    /**
     * Get message history<br>
     */
    public static final String GET_MESSAGE_HISTORY = "/chatmngr/msg/get";
    /**
     * Delete message history<br>
     */
    public static final String DELETE_MESSAGE_HISTORY = "/chatmngr/msg/delete";
    /**
     * Get repply message history<br>
     */
    public static final String GET_REPPLY_HISTORY = "/chatmngr/repply/get";
    /**
     * Get group info
     */
    public static final String GET_GROUP_INFO = "/chatmngr/group/getinfo";
    /**
     * Get member in group
     */
    public static final String GET_MEMBER_GROUP = "/chatmngr/group/getmbr";
    /**
     * Delete group
     */
    public static final String DELETE_GROUP = "/chatmngr/group/delete";
    /**
     * Get thread message history
     */
    public static final String GET_THREAD_HISTORY = "/chatmngr/thread/get";
    /**
     * Refresh token<br>
     */
    public static final String REFRESH_TOKEN = "/auth/refresh/token";
    // TASK manager
    /**
     * Get task<br>
     */
    public static final String GET_TASK = "/taskmngr/task/get";
    /**
     * Get task info<br>
     */
    public static final String GET_TASK_INFO = "/taskmngr/task/getinfo";
    /**
     * Save task<br>
     */
    public static final String SAVE_TASK = "/taskmngr/task/save";
    /**
     * Update task status<br>
     */
    public static final String UPDATE_TASK_STATUS = "/taskmngr/task/status/update";
    /**
     * Update task priority<br>
     */
    public static final String UPDATE_TASK_PRIORITY = "/taskmngr/task/priority/update";
    /**
     * Delete task<br>
     */
    public static final String DELETE_TASK = "/taskmngr/task/delete";
    public static final String MEDIAL_PATH = "/file/stream";
    public static final String MEDIAL_VARIABLE_PATH = "/file/stream/{filePath}";
    /**
     * restapi endpoint for get the count of chat message read status
     */
    public static final String CHAT_READ_STATUS_GET = "/chat/unread/get";
    /**
     * restapi endpoint for set the count of chat message read status
     */
    public static final String CHAT_READ_STATUS_SET = "/chat/unread/set";

}
