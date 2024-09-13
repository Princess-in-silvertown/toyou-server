package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.UserResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.persistance.GroupRepository;
import slvtwn.khu.toyouserver.persistance.MemberRepository;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserService {

    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    // TODO: 쿼리 / 구조 최적화 & N + 1
    public List<UserResponse> findUsersWithFilteringOptions(User user, String search, Long groupId) {
        if (groupId == null) {
            return findAllUsersWithSameGroups(user, search);
        }
        return findUsersWithSpecificGroup(user, search, groupId);
    }

    private List<UserResponse> findAllUsersWithSameGroups(User user, String search) {
        return memberRepository.findByUser(user).stream()
                .map(Member::getGroup)
                .map(each -> memberRepository.findByGroupAndUserNameLike(each, search))
                .flatMap(List::stream)
                .map(Member::getUser)
                .filter(each -> !each.getId().equals(user.getId()))
                .map(UserResponse::of)
                .toList();
    }

    private List<UserResponse> findUsersWithSpecificGroup(User user, String search, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ToyouException(ResponseType.GROUP_NOT_FOUND));

        return memberRepository.findByGroupAndUserNameLike(group, search).stream()
                .map(Member::getUser)
                .filter(each -> !each.getId().equals(user.getId()))
                .map(UserResponse::of)
                .toList();
    }
}