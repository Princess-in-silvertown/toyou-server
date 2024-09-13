package slvtwn.khu.toyouserver.common.feign.auth.kakao.web;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoUserResponse(
		String id,
		KakaoAccount kakaoAccount
) {
}