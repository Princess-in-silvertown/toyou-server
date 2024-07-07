package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.GroupService;
import slvtwn.khu.toyouserver.dto.GroupResponse;

@RequiredArgsConstructor
@RestController
public class GroupController {

    private final GroupService groupService;

    @PostMapping("/groups/{groupId}/members")
    public GroupResponse registerMember(@PathVariable long groupId) {
        return groupService.registerUser(groupId, 1L); // TODO: user -> argumentResolver 등록 필요
    }
}
