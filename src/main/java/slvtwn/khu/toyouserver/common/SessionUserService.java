package slvtwn.khu.toyouserver.common;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.SessionUser;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.SessionUserRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@Service
@Transactional(readOnly = true)
public class SessionUserService {

    private final SessionUserRepository sessionUserRepository;
    private final UserRepository userRepository;

    public SessionUserService(SessionUserRepository sessionUserRepository, UserRepository userRepository) {
        this.sessionUserRepository = sessionUserRepository;
        this.userRepository = userRepository;
    }

    public User findUserBySessionId(String sessionId) {
        SessionUser bySessionId = sessionUserRepository.findBySessionId(sessionId);
        return userRepository.findById(bySessionId.getUserId())
                .orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));
    }
}
