package slvtwn.khu.toyouserver.domain;

import static slvtwn.khu.toyouserver.common.ErrorType.USER_NOT_FOUND;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.exception.ToyouException;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Transactional(readOnly = true)
	Optional<User> findById(Long id);

	@Transactional(readOnly = true)
	default User getById(Long id) {
		return findById(id).orElseThrow(() -> new ToyouException(USER_NOT_FOUND));
	}
}
