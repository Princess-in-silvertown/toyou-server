package slvtwn.khu.toyouserver.application.auth.strategy;

import slvtwn.khu.toyouserver.dto.SocialAuthRequest;
import slvtwn.khu.toyouserver.dto.SocialAuthResponse;

public interface SocialAuthStrategy {
	SocialAuthResponse login(final SocialAuthRequest request);

	boolean support(String provider);
}
