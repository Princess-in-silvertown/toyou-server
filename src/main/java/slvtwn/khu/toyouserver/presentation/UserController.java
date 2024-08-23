package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

    @GetMapping("/me")
    public UserResponse getProfile(@UserAuthentication User user) {
        return UserResponse.of(user);
    }
}
