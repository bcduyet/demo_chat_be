package co.jp.xeex.chat.domains.admin.delete.user;

import co.jp.xeex.chat.base.ControllerBase;
import co.jp.xeex.chat.common.RestApiEndPoints;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DeleteUserController extends ControllerBase<DeleteUserRequest> {
    private DeleteUserService service;

    @Override
    @PostMapping(RestApiEndPoints.DELETE_USER)
    public final ResponseEntity<?> restApi(@RequestBody DeleteUserRequest request){
        super.preProcessRequest(request);
        DeleteUserResponse out = service.execute(request);
        return out != null ? createSuccessResponse(out) : createNullFailResponse();
    }
}
