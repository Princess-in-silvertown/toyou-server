package slvtwn.khu.toyouserver.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.ErrorType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.MemberRepository;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;
import slvtwn.khu.toyouserver.presentation.GroupMemberResponse;

@Service
@Transactional(readOnly = true)
public class GroupService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final MemberRepository memberRepository;

	public GroupService(GroupRepository groupRepository, UserRepository userRepository,
	                    MemberRepository memberRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public GroupResponse registerUser(long groupId, long userId) {
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ToyouException(ErrorType.GROUP_NOT_FOUND));
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));
		return new GroupResponse(group.getId(), group.getName());
	}

	@Transactional
	public GroupResponse create(String name) {
		var group = new Group(name);
		var savedGroup = groupRepository.save(group);
		return new GroupResponse(savedGroup.getId(), savedGroup.getName());

	}
}
