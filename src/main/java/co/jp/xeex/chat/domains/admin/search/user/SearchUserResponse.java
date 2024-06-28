package co.jp.xeex.chat.domains.admin.search.user;

import java.util.List;

import co.jp.xeex.chat.domains.admin.dto.UserDto;
import lombok.Data;

// UseCaseResponseDto is a data transfer object (DTO)
// that returns after processing the request 
//(a part as Response.data and wrapped in EntityResponse to be returned to the client).
@Data
public class SearchUserResponse {
    private int count;
    private List<UserDto> users;
}
