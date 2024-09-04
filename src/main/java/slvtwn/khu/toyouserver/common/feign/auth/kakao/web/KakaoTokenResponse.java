package slvtwn.khu.toyouserver.common.feign.auth.kakao.web;

public record KakaoTokenResponse(
		String accessToken,
		String refreshToken
) {
	public static KakaoTokenResponse of(String accessToken, String refreshToken) {
		return new KakaoTokenResponse(accessToken, refreshToken);
	}
}