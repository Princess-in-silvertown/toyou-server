package slvtwn.khu.toyouserver.application;

import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.common.ErrorType;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.UserRepository;
import slvtwn.khu.toyouserver.dto.UserResponse;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponse findUser(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));

		return new UserResponse(user.getId(), user.getName(), user.getProfilePicture());
	}
}
