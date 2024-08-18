package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByGroupId(Long groupId);

	@Query("SELECT m.group FROM Member m WHERE m.user.id = :userId")
	List<Group> findGroupsByUserId(Long userId);
}
