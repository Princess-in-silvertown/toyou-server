package slvtwn.khu.toyouserver.common;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.SessionUser;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.SessionUserRepository;

@Service
@Transactional(readOnly = true)
public class SessionUserService {

    private final SessionUserRepository sessionUserRepository;

    public SessionUserService(SessionUserRepository sessionUserRepository) {
        this.sessionUserRepository = sessionUserRepository;
    }

    public User findById(Long sessionId) {
        SessionUser sessionUser = sessionUserRepository.findById(sessionId)
                .orElseThrow(() -> new ToyouException(ErrorType.UNAUTHORIZED_USER_ACCESS));
        return sessionUser.getUser();
    }
}
