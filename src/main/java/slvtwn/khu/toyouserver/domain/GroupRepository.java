package slvtwn.khu.toyouserver.domain;

import static slvtwn.khu.toyouserver.common.ErrorType.GROUP_NOT_FOUND;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.exception.ToyouException;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	@Transactional(readOnly = true)
	Optional<Group> findById(long id);

	@Transactional(readOnly = true)
	default Group getById(final long id) {
		return findById(id).orElseThrow(() -> new ToyouException(GROUP_NOT_FOUND));
	}
}
