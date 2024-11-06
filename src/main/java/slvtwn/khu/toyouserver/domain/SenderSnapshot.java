package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
@Getter
public class SenderSnapshot {

	private Long senderId;
	private String name;
	private String profileImageUrl;

	public static SenderSnapshot of(User user) {
		return new SenderSnapshot(user.getId(), user.getName(), user.getProfilePicture());
	}
}
