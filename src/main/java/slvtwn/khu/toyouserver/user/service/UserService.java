package slvtwn.khu.toyouserver.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.user.domain.User;
import slvtwn.khu.toyouserver.user.dto.UserResponse;
import slvtwn.khu.toyouserver.user.exception.UserNotFoundException;
import slvtwn.khu.toyouserver.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserResponse findUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserResponse(user.getId(), user.getName(), user.getProfilePicture());
    }
}
