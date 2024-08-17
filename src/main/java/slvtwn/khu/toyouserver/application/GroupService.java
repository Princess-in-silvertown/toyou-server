package slvtwn.khu.toyouserver.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.MemberRepository;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.persistance.GroupRepository;

@Service
public class GroupService {

	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	private final MemberService memberService;

	public GroupService(GroupRepository groupRepository,
	                    MemberRepository memberRepository, MemberService memberService) {
		this.groupRepository = groupRepository;
		this.memberRepository = memberRepository;
		this.memberService = memberService;
	}

	@Transactional
	public GroupResponse registerMember(long groupId, long userId) {
		Group savedGroup = memberService.registerMember(groupId, userId).getGroup();
		return new GroupResponse(savedGroup.getId(), savedGroup.getName());
	}

	public void findMembers(long groupId) {

	}

	@Transactional
	public GroupResponse createGroup(String name) {
		Group group = new Group(name);
		Group savedGroup = groupRepository.save(group);
		return new GroupResponse(savedGroup.getId(), savedGroup.getName());
	}

	@Transactional(readOnly = true)
	public List<GroupResponse> findRegisteredGroups(long userId) {
		List<Group> groups = memberRepository.findGroupsByUserId(userId);
		return groups.stream()
				.map(group -> new GroupResponse(group.getId(), group.getName()))
				.toList();
	}
}
