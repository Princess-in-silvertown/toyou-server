package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@SpringBootTest
class GroupServiceTest {

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupService groupService;

	@DisplayName("그룹은 이름을 필수로 가진다.")
	@Test
	void 그룹은_이름으로_생성된다() {
		String groupName = "Name Created Group";
		Group group = new Group(groupName);
		groupRepository.save(group);

		GroupResponse groupResponse = groupService.create(groupName);
		assertThat(groupResponse.name()).isEqualTo(groupName);
	}

	@DisplayName("사용자는 그룹에 가입한다.")
	@Test
	void 사용자는_그룹에_가입한다() {
		Group group = new Group("Group");
		groupRepository.save(group);
		User user = new User("Hart", LocalDate.now(), "www.profile-picture.com");
		userRepository.save(user);

		GroupResponse groupResponse = groupService.registerUser(group.getId(), 1L);

		assertThat(groupResponse.id()).isEqualTo(group.getId());
		assertThat(groupResponse.name()).isEqualTo(group.getName());
	}

	@DisplayName("사용자는 가입한 그룹을 조회한다.")
	@Test
	void 사용자별_그룹을_조회한다() {
		Group group1 = new Group("Group-1"), group2 = new Group("Group-2");
		groupRepository.save(group1);
		groupRepository.save(group2);
		
		User user = new User("Hart", LocalDate.now(), "www.profile-picture.com");
		userRepository.save(user);

		groupService.registerUser(group1.getId(), user.getId());
		groupService.registerUser(group2.getId(), user.getId());

		List<GroupResponse> groups = groupService.findRegisteredGroups(user.getId());

		assertThat(groups).hasSize(2);
		assertThat(groups.get(0).name()).isEqualTo(group1.getName());
		assertThat(groups.get(1).name()).isEqualTo(group2.getName());
	}
}