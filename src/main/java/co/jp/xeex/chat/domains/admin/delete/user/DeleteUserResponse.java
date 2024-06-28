package co.jp.xeex.chat.domains.admin.delete.user;

import lombok.Data;

// DeleteUserResponseDto is a data transfer object (DTO)
// that returns after processing the request 
//(a part as Response.data and wrapped in EntityResponse to be returned to the client).
@Data
public class DeleteUserResponse {
    private boolean success;
}
