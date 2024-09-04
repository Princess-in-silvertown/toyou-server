package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.SocialAuthProvider;

public record SocialAuthResponse(
		Long userId,
		String userName,
		SocialAuthProvider provider,
		TokenResponse token
) {
	public static SocialAuthResponse of(Long userId, String userName, SocialAuthProvider provider,
	                                    TokenResponse token) {
		return new SocialAuthResponse(userId, userName, provider,
				TokenResponse.of(token.accessToken(), token.refreshToken()));
	}
}