package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.common.authentication.jwt.Token;
import slvtwn.khu.toyouserver.domain.SocialAuthProvider;

public record SocialAuthResponse(
		Long userId,
		String userName,
		SocialAuthProvider provider,
		Token token
) {
	public static SocialAuthResponse of(Long userId, String userName, SocialAuthProvider provider, Token token) {
		return new SocialAuthResponse(userId, userName, provider, token);
	}
}