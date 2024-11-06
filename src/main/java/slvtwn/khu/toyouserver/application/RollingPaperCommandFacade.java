package slvtwn.khu.toyouserver.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.dto.RollingPaperExchangeCountResponse;

@Component
@RequiredArgsConstructor
public class RollingPaperCommandFacade {

	private final RollingPaperService rollingPaperService;
	private final UserService userService;

	public RollingPaperExchangeCountResponse getExchangeCount(Long userId) {
		return RollingPaperExchangeCountResponse.of(
				userService.getProfile(userId).name(),
				rollingPaperService.countSentRollingPapers(userId),
				rollingPaperService.countReceivedRollingPapers(userId)
		);
	}
}
