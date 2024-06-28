package co.jp.xeex.chat.domains.chat.unread;

public class SetChatUnreadRequest extends GetChatUnreadRequest {
    /**
     * the count of unread messages<br>    
     * when [setReadMessageCount] api is called, this is the number of read messages
     * will be updated by client.
     * setReadMessageCount use both negative/positive numbers values. (set or clear
     * read status)<br>
     */
    public Integer unreadCount;
}
