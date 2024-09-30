package slvtwn.khu.toyouserver.presentation;

import java.util.List;
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

	@GetMapping("/rollingpapers/{rollingPaperId}")
	public ToyouResponse<RollingPaperResponse> findById(@UserAuthentication Long userId,
	                                                    @PathVariable Long rollingPaperId) {
		return ToyouResponse.from(rollingPaperService.findById(userId, rollingPaperId));
	}

	@GetMapping("/rollingpapers")
	public ToyouResponse<List<RollingPaperResponse>> findReceivedRollingPapers(@UserAuthentication Long userId,
	                                                                           @RequestParam(required = false) Long groupId,
	                                                                           @RequestParam(defaultValue = "0") Long targetId,
	                                                                           @RequestParam(defaultValue = "10") int limit) {
		return ToyouResponse.from(rollingPaperService.findReceivedRollingPapers(userId, groupId, targetId, limit));
	}

	@PostMapping("/users/{userId}/rollingpapers")
	public ToyouResponse<Void> sendRollingPaper(@UserAuthentication Long RequestUserId,
	                                            @PathVariable(name = "userId") Long recipientUserId,
	                                            @RequestBody RollingPaperRequest rollingPaperRequest) {
		rollingPaperService.sendRollingPaper(recipientUserId, rollingPaperRequest);
		return ToyouResponse.noContent();
	}

	// TODO: ModelLabs 관련 문제로 DISABLED
	@Deprecated
	@PostMapping("/rollingpapers/{rollingPaperId}/generate-cover")
	public ToyouResponse<Void> generateCoverImage(@UserAuthentication Long userId,
	                                              @PathVariable Long rollingPaperId,
	                                              @RequestBody CoverRequest request) {
		rollingPaperService.generateCoverImageAndUpdateRollingPaper(request, rollingPaperId);
		return ToyouResponse.noContent();
	}
}
