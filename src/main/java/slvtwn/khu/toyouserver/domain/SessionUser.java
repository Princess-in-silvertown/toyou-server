package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slvtwn.khu.toyouserver.common.BaseTimeEntity;

@Entity
@Table(name = "session_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SessionUser extends BaseTimeEntity {

	@Id
	@Column(name = "session_id", nullable = false)
	private String sessionId;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	// TODO ; 카드

	public SessionUser(String sessionId, Long userId) {
		this.sessionId = sessionId;
		this.userId = userId;
	}
}
