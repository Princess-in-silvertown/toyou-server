package slvtwn.khu.toyouserver.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.ErrorType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@Service
@Transactional(readOnly = true)
public class GroupService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;

	public GroupService(GroupRepository groupRepository, UserRepository userRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public GroupResponse registerUser(long groupId, long userId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ToyouException(ErrorType.GROUP_NOT_FOUND));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));

		// TODO : 그룹에 유저를 가입시킨다. 유저는 멤버로 등록된다.
//		group.addMember(user);
		return new GroupResponse(group.getId(), group.getName());
	}

	@Transactional
	public GroupResponse create(String name) {
		return Optional.of(new Group(name))
				.map(groupRepository::save)
				.map(group -> new GroupResponse(group.getId(), group.getName()))
				.orElseThrow(() -> new ToyouException(ErrorType.INVALID_GROUP_DATA));
	}
}
