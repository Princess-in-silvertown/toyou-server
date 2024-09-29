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
import slvtwn.khu.toyouserver.dto.UserResponse;
import slvtwn.khu.toyouserver.dto.UserUpdateRequest;

@RequiredArgsConstructor
@RestController
public class
UserController {

	private final UserService userService;

	@GetMapping("/me")
	public ToyouResponse<UserResponse> getProfile(@UserAuthentication Long userId) {
		return ToyouResponse.from(userService.getProfile(userId));
	}

	@PutMapping("/users")
	public void updateProfile(@UserAuthentication Long userId, @RequestBody UserUpdateRequest request) {
		userService.updateUser(userId, request);
	}

	@GetMapping("/users")
	public ToyouResponse<List<UserResponse>> findMembersWithFilteringOptions(
			@UserAuthentication Long userId,
			@RequestParam String search, @RequestParam(required = false) Long groupId) {
		return ToyouResponse.from(userService.findUsersWithFilteringOptions(userId, search, groupId));
	}
}
