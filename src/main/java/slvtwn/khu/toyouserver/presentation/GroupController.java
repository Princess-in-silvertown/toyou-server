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
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.GroupCreateRequest;
import slvtwn.khu.toyouserver.dto.GroupMemberResponse;
import slvtwn.khu.toyouserver.dto.GroupResponse;
import slvtwn.khu.toyouserver.dto.GroupsResponse;

@RequiredArgsConstructor
@RestController
public class GroupController {

	private final GroupService groupService;

	@PostMapping("/groups")
	public ToyouResponse<GroupResponse> createGroup(@RequestBody GroupCreateRequest request) {
		return ToyouResponse.from(groupService.createGroup(request));
	}

	@GetMapping("/groups")
	public ToyouResponse<GroupsResponse> findGroups(@UserAuthentication Long userId,
													String keyword) {
		return ToyouResponse.from(groupService.findGroups(userId, keyword));
	}

	@PostMapping("/groups/{groupId}/register")
	public void registerMember(@UserAuthentication Long userId, @PathVariable Long groupId) {
		groupService.registerMember(groupId, userId);
	}

	@GetMapping("/groups/{groupId}/members")
	public ToyouResponse<List<GroupMemberResponse>> findMembers(@PathVariable Long groupId) {
		return ToyouResponse.from(groupService.findMembers(groupId));
	}
}
