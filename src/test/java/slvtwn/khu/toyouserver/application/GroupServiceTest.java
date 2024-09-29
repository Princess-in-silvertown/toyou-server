package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupCreateRequest;
import slvtwn.khu.toyouserver.dto.GroupMemberResponse;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class GroupServiceTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private GroupService groupService;

	@Test
	void 그룹을_생성할_수_있다() {
		// given
		GroupCreateRequest request = new GroupCreateRequest("group_name");

		// when
		GroupResponse response = groupService.createGroup(request);

		// then
		assertThat(response.name()).isEqualTo(request.name());
	}

	@Test
	void 유저는_그룹에_가입할_수_있다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_pic", null);
		Group group = new Group("group_name");

		entityManager.persist(user);
		entityManager.persist(group);

		// when, then
		assertThatCode(() -> groupService.registerMember(group.getId(), user.getId()))
				.doesNotThrowAnyException();
	}

	@Test
	void 그룹이_존재하지_않는다면_가입이_불가능하다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_pic", null);
		long groupIdNonExists = 1L;

		entityManager.persist(user);

		// when, then
		assertThatThrownBy(() -> groupService.registerMember(groupIdNonExists, user.getId()))
				.isInstanceOf(ToyouException.class);
	}

	@Test
	void 그룹에_속한_멤버들을_찾을_수_있다() {
		// given
		User user1 = new User("name1", LocalDate.now(), "introduction", "profile_pic", null);
		User user2 = new User("name2", LocalDate.now(), "introduction", "profile_pic", null);

		Group group = new Group("group_name");

		Member member1 = new Member(user1, group);
		Member member2 = new Member(user2, group);

		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(group);
		entityManager.persist(member1);
		entityManager.persist(member2);

		// when
		List<GroupMemberResponse> response = groupService.findMembers(group.getId());

		// then
		assertThat(response).extracting(GroupMemberResponse::name)
				.containsExactlyInAnyOrder(user1.getName(), user2.getName());
	}

	@Test
	void 유저는_가입한_그룹을_조회할_수_있다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_pic", null);
		Group group1 = new Group("group_name1");
		Group group2 = new Group("group_name2");
		Member member1 = new Member(user, group1);
		Member member2 = new Member(user, group2);

		entityManager.persist(user);
		entityManager.persist(group1);
		entityManager.persist(group2);
		entityManager.persist(member1);
		entityManager.persist(member2);

		// when
		List<GroupResponse> response = groupService.findGroups(user.getId(), null).groups();

		// then
		assertThat(response).extracting(GroupResponse::name)
				.containsExactlyInAnyOrder(group1.getName(), group2.getName());
	}

	@Test
	void 이름을_기준으로_그룹을_검색한다() {
		// given
		Group group = new Group("경희어린이집", "경기도 용인시 기흥구 덕영대로 1732", "경기도", "https://www.khu.ac.kr");
		entityManager.persist(group);
		String keyword = "경희";

		// when
		List<GroupResponse> response = groupService.findGroups(null, keyword).groups();

		// then
		assertThat(response).containsExactlyInAnyOrder(new GroupResponse(group.getId(), group.getName()));
	}

	@Test
	void 검색어가_포함된_그룹이_없다면_빈_리스트를_반환한다() {
		// given
		String keyword = "섭섭어린이집";

		// when
		List<GroupResponse> response = groupService.findGroups(null, keyword).groups();

		// then
		assertThat(response).isEmpty();
	}

	@Test
	void 검색어가_포함된_그룹_여러_개를_반환할_수_있다() {
		// given
		Group group1 = new Group("경희어린이집1", "경기도 용인시 기흥구 덕영대로 1732", "경기도", "https://www.khu.ac.kr");
		Group group2 = new Group("경희어린이집2", "경기도 용인시 기흥구 덕영대로 1732", "경기도", "https://www.khu.ac.kr");
		entityManager.persist(group1);
		entityManager.persist(group2);
		String keyword = "경희어린이집";

		// when
		List<GroupResponse> response = groupService.findGroups(null, keyword).groups();

		// then
		assertThat(response).containsExactlyInAnyOrder(
				new GroupResponse(group1.getId(), group1.getName()),
				new GroupResponse(group2.getId(), group2.getName()));
	}
}
