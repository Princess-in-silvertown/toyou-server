package slvtwn.khu.toyouserver.application;

import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.domain.UserRepository;


@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User findById(final long userId) {
		return userRepository.getById(userId);
	}
}
