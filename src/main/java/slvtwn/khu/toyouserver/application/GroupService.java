package slvtwn.khu.toyouserver.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.ErrorType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.dto.GroupCreateRequest;
import slvtwn.khu.toyouserver.persistance.MemberRepository;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupMemberResponse;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerMember(long groupId, long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ToyouException(ErrorType.GROUP_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ToyouException(ErrorType.USER_NOT_FOUND));

        memberRepository.save(new Member(user, group));
    }

    public List<GroupMemberResponse> findMembers(long groupId) {
        return memberRepository.findByGroupId(groupId).stream()
                .map(GroupMemberResponse::of)
                .toList();
    }

    @Transactional
    public GroupResponse createGroup(GroupCreateRequest request) {
        Group group = new Group(request.name());
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
