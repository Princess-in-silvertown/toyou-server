package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.RollingPaper;

public interface RollingPaperRepository extends JpaRepository<RollingPaper, Long> {

	@Query("SELECT r FROM RollingPaper r WHERE r.member.id IN :memberIds AND r.id >= :targetId ORDER BY r.id DESC")
	Slice<RollingPaper> findAllByMembersAfterCursor(@Param("memberIds") List<Long> memberIds,
	                                                @Param("targetId") Long targetId,
	                                                Pageable pageable);

	void deleteAllByMemberIn(List<Member> members);
}
