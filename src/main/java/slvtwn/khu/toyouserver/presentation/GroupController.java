package slvtwn.khu.toyouserver.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.GroupService;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.GroupCreateRequest;
import slvtwn.khu.toyouserver.dto.GroupMemberResponse;
import slvtwn.khu.toyouserver.dto.GroupResponse;

@RequiredArgsConstructor
@RestController
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups")
    public GroupResponse createGroup(@RequestBody GroupCreateRequest request) {
        return groupService.createGroup(request);
    }

    @GetMapping("/groups")
    public List<GroupResponse> findRegisteredGroups(@UserAuthentication User user) {
        return groupService.findRegisteredGroupsByUser(user);
    }

    @PostMapping("/groups/{groupId}/members")
    public void registerMember(@UserAuthentication User user, @PathVariable Long groupId) {
        groupService.registerMember(groupId, user);
    }

    @GetMapping("/groups/{groupId}/members")
    public List<GroupMemberResponse> findMembers(@PathVariable Long groupId) {
        return groupService.findMembers(groupId);
    }
}
