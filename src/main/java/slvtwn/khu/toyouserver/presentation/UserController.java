package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.UserService;
import slvtwn.khu.toyouserver.common.UserAuthentication;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserResponse;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping("/users")
	public UserResponse findUser(@UserAuthentication User user) {
		return userService.findUser(user.getId());
	}
}
