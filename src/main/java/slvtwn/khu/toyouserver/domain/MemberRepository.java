package slvtwn.khu.toyouserver.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByGroupId(Long groupId);

	@Query("SELECT m.group FROM Member m WHERE m.user.id = :userId")
	List<Group> findGroupsByUserId(Long userId);
}
