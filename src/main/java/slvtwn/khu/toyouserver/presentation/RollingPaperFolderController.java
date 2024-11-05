package slvtwn.khu.toyouserver.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.RollingPaperCommandFacade;
import slvtwn.khu.toyouserver.common.authentication.UserAuthentication;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.RollingPaperExchangeCountResponse;

@RequestMapping("/rollingpapers/folders")
@RequiredArgsConstructor
@RestController
public class RollingPaperFolderController {

	private final RollingPaperCommandFacade rollingPaperCommandFacade;

	@GetMapping("/info")
	public ToyouResponse<RollingPaperExchangeCountResponse> findRollingPaperExchangeCount(
			@UserAuthentication Long userId) {
		return ToyouResponse.from(rollingPaperCommandFacade.getExchangeCount(userId));
	}
}
