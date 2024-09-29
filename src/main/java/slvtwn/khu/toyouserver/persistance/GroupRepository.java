package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import slvtwn.khu.toyouserver.domain.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g where g.name like %:keyword%")
    List<Group> findByNameLike(String keyword);
}
