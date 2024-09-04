package slvtwn.khu.toyouserver.presentation;

import static slvtwn.khu.toyouserver.common.response.ResponseType.NOT_SUPPORTED_AUTH_PROVIDER;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import slvtwn.khu.toyouserver.application.auth.SocialAuthContext;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.SocialAuthRequest;
import slvtwn.khu.toyouserver.dto.SocialAuthResponse;
import slvtwn.khu.toyouserver.exception.ToyouException;

@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final SocialAuthContext socialAuthContext;

	@PostMapping("/login")
	public ToyouResponse<SocialAuthResponse> login(@RequestBody SocialAuthRequest request) {
		if (socialAuthContext.support(request.provider())) {
			return ToyouResponse.from(socialAuthContext.doLogin(request));
		}
		throw new ToyouException(NOT_SUPPORTED_AUTH_PROVIDER);
	}
}