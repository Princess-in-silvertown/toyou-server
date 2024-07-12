package slvtwn.khu.toyouserver.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByGroupId(Long groupId);
}
