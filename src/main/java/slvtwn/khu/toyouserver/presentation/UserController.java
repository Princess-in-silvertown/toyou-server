package slvtwn.khu.toyouserver.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.UserService;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserUpdateRequest;
import slvtwn.khu.toyouserver.dto.UserResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ToyouResponse<UserResponse> getProfile(@UserAuthentication User user) {
        return ToyouResponse.from(UserResponse.of(user));
    }

    @PutMapping("/users")
    public void updateProfile(@UserAuthentication User user, @RequestBody UserUpdateRequest request) {
        userService.updateUser(user, request);
    }

    @GetMapping("/users")
    public ToyouResponse<List<UserResponse>> findMembersWithFilteringOptions(
            @UserAuthentication User user,
            @RequestParam String search, @RequestParam(required = false) Long groupId) {
        return ToyouResponse.from(userService.findUsersWithFilteringOptions(user, search, groupId));
    }
}
