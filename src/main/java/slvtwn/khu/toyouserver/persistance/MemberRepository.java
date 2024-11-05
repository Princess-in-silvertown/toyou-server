package slvtwn.khu.toyouserver.persistance;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;

public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByGroupId(Long groupId);

	List<Member> findByUser(User user);

	List<Member> findByUserId(Long userId);

	@Query("select m from Member m join m.user u where m.group = :group and u.name like %:pattern%")
	List<Member> findByGroupAndUserNameLike(Group group, String pattern);

	Optional<Member> findByUserIdAndGroupId(Long userId, Long groupId);

	void deleteByUserAndGroupIn(User user, List<Group> group);

	List<Member> findAllByGroupIn(List<Group> removedGroups);
}
