package slvtwn.khu.toyouserver.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.common.ToyouResponse;
import slvtwn.khu.toyouserver.user.dto.UserResponse;
import slvtwn.khu.toyouserver.user.service.UserService;

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
