package co.jp.xeex.chat.domains.file.stream;

import co.jp.xeex.chat.base.RequestBase;

/**
 * Request to view media files (video/audio)
 */
public class StreamRequest extends RequestBase {
   /**
     * Relative path of the file, including file name<br>
     * This path was previously generated and sent by the server to the client<br>     
     * Example: /chat/yyyy/mm/groupId/media/uuid.mp4
     */
    public String filePath;
}
