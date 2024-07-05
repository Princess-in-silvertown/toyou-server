package slvtwn.khu.toyouserver.presentation;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.GroupService;

@RestController
public class GroupController {

	private final GroupService groupService;

	public GroupController(GroupService groupService) {
		this.groupService = groupService;
	}

	@PostMapping("/groups/{groupId}/members")
	public GroupResponse registerMember(@PathVariable final long groupId) {
		return groupService.registerMember(groupId);
	}
}
