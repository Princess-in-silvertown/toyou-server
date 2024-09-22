package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import slvtwn.khu.toyouserver.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
	@Query("select s from School s where s.name like %:keyword%")
	List<School> findByNameContaining(String keyword);
}