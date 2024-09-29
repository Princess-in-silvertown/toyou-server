package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupCreateRequest;
import slvtwn.khu.toyouserver.dto.GroupMemberResponse;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.MemberRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GroupService {

	private final GroupRepository groupRepository;
	private final MemberRepository memberRepository;
	private final UserRepository userRepository;

	@Transactional
	public GroupResponse createGroup(GroupCreateRequest request) {
		Group group = new Group(request.name());
		Group savedGroup = groupRepository.save(group);
		return new GroupResponse(savedGroup.getId(), savedGroup.getName());
	}

	@Transactional
	public void registerMember(Long groupId, Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		Group group = groupRepository.findById(groupId)
				.orElseThrow(() -> new ToyouException(ResponseType.GROUP_NOT_FOUND));

		memberRepository.save(new Member(user, group));
	}

	public List<GroupMemberResponse> findMembers(long groupId) {
		return memberRepository.findByGroupId(groupId).stream()
				.map(GroupMemberResponse::of)
				.toList();
	}

	public List<GroupResponse> findGroups(Long userId, String keyword) {
		if (keyword == null) {
			return findRegisteredGroups(userId);
		}
		return findGroupsByKeywords(keyword);
	}

	private List<GroupResponse> findRegisteredGroups(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ToyouException(ResponseType.USER_NOT_FOUND));
		// TODO: 쿼리 최적화
		return memberRepository.findByUser(user).stream()
				.map(Member::getGroup)
				.map(each -> new GroupResponse(each.getId(), each.getName()))
				.toList();
	}

	private List<GroupResponse> findGroupsByKeywords(String keyword) {
		return groupRepository.findTop50ByNameLike(keyword).stream()
				.map(each -> new GroupResponse(each.getId(), each.getName()))
				.toList();
	}
}
