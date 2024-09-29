package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.RollingPaperService;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;

@RestController
@RequiredArgsConstructor
public class RollingPaperController {

	private final RollingPaperService rollingPaperService;

	@GetMapping("/rollingpapers")
	public ToyouResponse<RollingPaperResponse> findById(@UserAuthentication Long userId,
	                                                    @RequestParam Long rollingPaperId) {
		return ToyouResponse.from(rollingPaperService.findById(userId, rollingPaperId));
	}

	@GetMapping("/rollingpapers")
	public ToyouResponse<List<RollingPaperResponse>> findReceivedRollingPapers(@UserAuthentication Long userId,
	                                                                           @RequestParam Long groupId,
	                                                                           @RequestParam Long targetId,
	                                                                           @RequestParam Integer limit) {
		return ToyouResponse.from(rollingPaperService.findReceivedRollingPapers(userId, groupId, targetId, limit));
	}

	@PostMapping("/users/{userId}/rollingpapers")
	public void sendRollingPaper(@UserAuthentication Long RequestUserId,
	                             @PathVariable(name = "userId") Long recipientUserId,
	                             @RequestBody RollingPaperRequest rollingPaperRequest) {
		rollingPaperService.sendRollingPaper(recipientUserId, rollingPaperRequest);
	}

	@PostMapping("/rollingpapers/{rollingPaperId}/generate-cover")
	public void generateCoverImage(@UserAuthentication Long userId,
	                               @PathVariable Long rollingPaperId, @RequestBody CoverRequest request) {
		rollingPaperService.generateCoverImageAndUpdateRollingPaper(request, rollingPaperId);
	}
}
