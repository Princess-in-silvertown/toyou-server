package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.SessionUser;

public interface SessionUserRepository extends JpaRepository<SessionUser, Long> {
}
