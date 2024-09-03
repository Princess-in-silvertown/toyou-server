package slvtwn.khu.toyouserver.common.feign.auth.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoResourceApiClient", url = "https://kapi.kakao.com")
public interface KakaoResourceApiClient {

	@GetMapping(value = "/v2/user/me")
	KakaoUserResponse getUserInformation(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}