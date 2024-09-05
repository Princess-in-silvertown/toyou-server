package slvtwn.khu.toyouserver.common.authentication.social;

import static slvtwn.khu.toyouserver.common.response.ResponseType.NOT_SUPPORTED_AUTH_PROVIDER;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.common.authentication.social.strategy.KakaoAuthStrategy;
import slvtwn.khu.toyouserver.common.authentication.social.strategy.SocialAuthStrategy;
import slvtwn.khu.toyouserver.domain.SocialAuthProvider;
import slvtwn.khu.toyouserver.dto.SocialAuthRequest;
import slvtwn.khu.toyouserver.dto.SocialAuthResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;

@Component
@RequiredArgsConstructor
public class SocialAuthContext {

	private final KakaoAuthStrategy kakaoAuthStrategy;

	private final List<SocialAuthStrategy> socialAuthStrategies = new ArrayList<>();

	@PostConstruct
	void initSocialLoginContext() {
		socialAuthStrategies.add(kakaoAuthStrategy);
	}

	public boolean support(SocialAuthProvider provider) {
		for (SocialAuthStrategy strategy : socialAuthStrategies) {
			if (strategy.support(provider.toString())) {
				return true;
			}
		}
		return false;
	}

	public SocialAuthResponse doLogin(final SocialAuthRequest request) {
		for (SocialAuthStrategy strategy : socialAuthStrategies) {
			if (strategy.support(request.provider().toString())) {
				return strategy.login(request);
			}
		}
		throw new ToyouException(NOT_SUPPORTED_AUTH_PROVIDER);
	}
}