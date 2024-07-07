package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.UserService;
import slvtwn.khu.toyouserver.common.ToyouResponse;
import slvtwn.khu.toyouserver.dto.UserResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{userId}")
    public ToyouResponse<UserResponse> findUser(@PathVariable Long userId) {
        UserResponse userResponse = userService.findUser(userId);

        return ToyouResponse.success(userResponse);
    }
}
