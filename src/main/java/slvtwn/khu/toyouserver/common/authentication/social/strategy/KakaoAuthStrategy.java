package slvtwn.khu.toyouserver.common.authentication.social.strategy;

import static slvtwn.khu.toyouserver.domain.SocialAuthProvider.KAKAO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.common.authentication.jwt.JwtProvider;
import slvtwn.khu.toyouserver.common.authentication.jwt.Token;
import slvtwn.khu.toyouserver.common.feign.auth.kakao.KakaoAuthApiClient;
import slvtwn.khu.toyouserver.common.feign.auth.kakao.KakaoResourceApiClient;
import slvtwn.khu.toyouserver.common.feign.auth.kakao.web.KakaoTokenResponse;
import slvtwn.khu.toyouserver.common.feign.auth.kakao.web.KakaoUserResponse;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.domain.UserOAuthCredential;
import slvtwn.khu.toyouserver.domain.UserOAuthCredentialRepository;
import slvtwn.khu.toyouserver.dto.SocialAuthRequest;
import slvtwn.khu.toyouserver.dto.SocialAuthResponse;
import slvtwn.khu.toyouserver.persistance.UserRepository;

@Service
@RequiredArgsConstructor
public class KakaoAuthStrategy implements SocialAuthStrategy {

	@Value("${oauth.kakao.client-id}")
	private String kakaoClientId;
	@Value("${oauth.kakao.redirect-uri}")
	private String kakaoRedirectUri;
	@Value("${oauth.kakao.grant-type}")
	private String grantType;

	private final KakaoAuthApiClient kakaoAuthApiClient;
	private final KakaoResourceApiClient kakaoResourceApiClient;

	private final UserRepository userRepository;
	private final UserOAuthCredentialRepository userOAuthCredentialRepository;

	private final JwtProvider jwtProvider;

	@Override
	@Transactional
	public SocialAuthResponse login(SocialAuthRequest request) {
		KakaoTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
				"authorization_code",
				kakaoClientId,
				kakaoRedirectUri,
				request.authorizationCode()
		);
		KakaoUserResponse userResponse = kakaoResourceApiClient.getUserInformation(
				"Bearer " + tokenResponse.accessToken());
		User user = handleUserOAuthCredential(userResponse);
		Token token = jwtProvider.issueTokens(user.getId());
		return SocialAuthResponse.of(user.getId(), user.getName(), KAKAO, token);
	}

	@Override
	public boolean support(String provider) {
		return provider.equals("KAKAO");
	}

	private User handleUserOAuthCredential(KakaoUserResponse userResponse) {
		UserOAuthCredential userOAuthCredential = userOAuthCredentialRepository.findBySerialId(userResponse.serialId())
				.orElse(null);

		User authenticatedUser;
		if (userOAuthCredential == null) {
			authenticatedUser = User.create(userResponse.kakaoAccount().profile().nickname(),
					userResponse.kakaoAccount().profile().profileImageUrl());
			userOAuthCredential = UserOAuthCredential.recordKAKAOCredential(authenticatedUser, userResponse.serialId());

			userRepository.save(authenticatedUser);
			userOAuthCredentialRepository.save(userOAuthCredential);
		} else {
			authenticatedUser = userOAuthCredential.getUser();
		}
		return authenticatedUser;
	}
}