package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
