package slvtwn.khu.toyouserver.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
