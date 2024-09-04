package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slvtwn.khu.toyouserver.common.entity.BaseTimeEntity;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private LocalDate birthday;

	private String introduction;

	private String profilePicture;

	public User(String name, LocalDate birthday, String introduction, String profilePicture) {
		this.name = name;
		this.birthday = birthday;
		this.introduction = introduction;
		this.profilePicture = profilePicture;
	}

	public static User create(String name, String profilePicture) {
		return new User(name, null, null, profilePicture);
	}
}
