package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slvtwn.khu.toyouserver.domain.SessionUser;

@Repository
public interface SessionUserRepository extends JpaRepository<SessionUser, Long> {
	SessionUser findBySessionId(String sessionId);
}
