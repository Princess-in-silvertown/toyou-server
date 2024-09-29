package slvtwn.khu.toyouserver.domain;


import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_social_accounts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserSocialAccount {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private SocialAuthProvider provider;
	private String providerSerial;
	private Long userId;

	public static UserSocialAccount createAccountByKakao(Long userId, String providerSerial) {
		return new UserSocialAccount(null, SocialAuthProvider.KAKAO, providerSerial, userId);
	}
}
