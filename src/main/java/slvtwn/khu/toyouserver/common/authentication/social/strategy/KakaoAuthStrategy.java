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
	private String kakaoGrantType;

	private final KakaoAuthApiClient kakaoAuthApiClient;
	private final KakaoResourceApiClient kakaoResourceApiClient;

	private final UserRepository userRepository;

	private final JwtProvider jwtProvider;

	// TODO: 파사드로 카카오 요청 + 유저 저장 부분 분리
	// 현재 외부 네트워크에 트랜잭션이 의존하는 상태
	@Override
	@Transactional
	public SocialAuthResponse login(SocialAuthRequest request) {
		KakaoTokenResponse tokenResponse = kakaoAuthApiClient.getOAuth2AccessToken(
				kakaoGrantType,
				kakaoClientId,
				kakaoRedirectUri,
				request.authorizationCode()
		);
		KakaoUserResponse userResponse = kakaoResourceApiClient.getUserInformation(
				"Bearer " + tokenResponse.accessToken());
		User user = findOrCreateUser(userResponse);
		Token token = jwtProvider.issueTokens(user.getId());
		return SocialAuthResponse.of(user.getId(), user.getName(), KAKAO, token);
	}

	@Override
	public boolean support(String provider) {
		return provider.equals("KAKAO");
	}

	private User findOrCreateUser(KakaoUserResponse userResponse) {
		return userRepository.findByProviderSerial(userResponse.id())
				.orElseGet(() -> registerUser(userResponse));
	}

	private User registerUser(KakaoUserResponse userResponse) {
		User user = User.create(
				userResponse.kakaoAccount().profile().nickname(),
				userResponse.kakaoAccount().profile().profileImageUrl(),
				KAKAO,
				userResponse.id()
		);
		userRepository.save(user);
		return user;
	}
}