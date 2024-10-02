package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	List<Group> findTop30ByNameLike(String keyword);
	List<Group> findAllByIdIn(List<Long> groupIds);
}
