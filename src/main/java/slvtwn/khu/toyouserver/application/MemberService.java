package slvtwn.khu.toyouserver.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.MemberResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    public MemberService(MemberRepository memberRepository, GroupRepository groupRepository) {
        this.memberRepository = memberRepository;
        this.groupRepository = groupRepository;
    }

    // TODO: 쿼리 / 구조 최적화
    public List<MemberResponse> findMembersWithFilteringOptions(User user, String search, Long groupId) {
        if (groupId == null) {
            return findAllMembersWithSameGroups(user, search);
        }
        List<MemberResponse> result = findMembersWithSpecificGroup(user, search, groupId);
        return result;
    }

    private List<MemberResponse> findAllMembersWithSameGroups(User user, String search) {
        return memberRepository.findByUser(user).stream()
                .map(Member::getGroup)
                .map(each -> memberRepository.findByGroupAndUserNameLike(each, search))
                .flatMap(List::stream)
                .filter(each -> !each.getUser().getId().equals(user.getId()))
                .map(MemberResponse::of)
                .toList();
    }

    private List<MemberResponse> findMembersWithSpecificGroup(User user, String search, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ToyouException(ResponseType.GROUP_NOT_FOUND));

        return memberRepository.findByGroupAndUserNameLike(group, search).stream()
                .filter(each -> !each.getUser().getId().equals(user.getId()))
                .map(MemberResponse::of)
                .toList();
    }
}
