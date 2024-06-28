package co.jp.xeex.chat.domains.usecase;

import lombok.Data;

// UseCaseResponseDto is a data transfer object (DTO)
// that returns after processing the request 
//(a part as Response.data and wrapped in EntityResponse to be returned to the client).
@Data
public class UseCaseResponse {
    private String field1;
    private Long field2;    
    // ...

}
