package slvtwn.khu.toyouserver.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import slvtwn.khu.toyouserver.domain.Cover;

public interface CoverRepository extends JpaRepository<Cover, String> {

}
