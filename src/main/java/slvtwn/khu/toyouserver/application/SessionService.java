package slvtwn.khu.toyouserver.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Session;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.SessionRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SessionService {

    private final SessionRepository sessionRepository;

    public User findById(Long sessionId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ToyouException(ResponseType.UNAUTHORIZED_USER_ACCESS));
        return session.getUser();
    }
}
