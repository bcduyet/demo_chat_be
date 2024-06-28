package co.jp.xeex.chat.domains.chat.unread;

import co.jp.xeex.chat.exception.BusinessException;

/**
 * Provide read status of chat messages
 * 
 * @author v_long
 */
public interface ChatUnreadService {
    /**
     * set the unread count for the groupId of the user.
     * 
     * @param request contains the groupId of the user would be set unread count
     */
    SetChatUnreadResponse setUnread(SetChatUnreadRequest request);

    /**
     * get the number of unread countfor the groupId of the user.
     * 
     * @param request contains the groupId of the user would be get unread count
     */
    GetChatUnreadReseponse getUnread(GetChatUnreadRequest request) throws BusinessException;

    /**
     * This method ensures that whenever a new message is sent to a chat group, <br>
     * the unread message count for each member of the group is incremented, <br>
     * allowing the application to track and display the number of unread messages
     * <br>
     * for each user in each chat group.
     */
    void increamentUnreadCountForChatGroup(String chatGroupId);

    /**
     * This method ensures that whenever a new message is sent to a friend, <br>
     * the unread message count for friend will be incremented to 1
     * @param friendCd the friend who received the message
     */
    void increamentUnreadCountForChatFriend(String chatGroupId,String friendCd);
}
