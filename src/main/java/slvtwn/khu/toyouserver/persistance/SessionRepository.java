package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
