package slvtwn.khu.toyouserver.application;

import java.util.List;
import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.domain.GroupRepository;
import slvtwn.khu.toyouserver.presentation.GroupMemberResponse;
import slvtwn.khu.toyouserver.presentation.GroupResponse;

@Service
public class GroupService {

	private final GroupRepository groupRepository;

	private final UserService userService;

	public GroupService(GroupRepository groupRepository, UserService userService) {
		this.groupRepository = groupRepository;
		this.userService = userService;
	}

	public GroupResponse registerUser(final long groupId, final long userId) {
		final var group = groupRepository.getById(groupId);
		final var user = userService.findById(userId);
		// TODO : 그룹에 유저를 가입시킨다. 유저는 멤버로 등록된다.
//		group.addMember(user);
		return new GroupResponse(group.getId(), group.getName());
	}

	public List<GroupMemberResponse> findAllMembers(final long groupId) {
		final var group = groupRepository.getById(groupId);
		return List.of(new GroupMemberResponse(1L, 1L, "user1", "profile1"));
	}
}