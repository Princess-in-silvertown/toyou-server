package slvtwn.khu.toyouserver.common.authentication.jwt;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record Token(
		String accessToken,
		String refreshToken
) {
	public static Token of(String accessToken, String refreshToken) {
		return new Token(accessToken, refreshToken);
	}
}