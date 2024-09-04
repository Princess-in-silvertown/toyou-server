package slvtwn.khu.toyouserver.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SocialAuthProvider {
	
	KAKAO("kakao");

	private final String value;

}