package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slvtwn.khu.toyouserver.common.entity.BaseTimeEntity;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private LocalDate birthday;

	private String introduction;

	private String profilePicture;

	@Enumerated(EnumType.STRING)
	private SocialAuthProvider provider;

	private String providerSerial;

	public User(String name, LocalDate birthday, String introduction, String profilePicture,
	            SocialAuthProvider provider) {
		this.name = name;
		this.birthday = birthday;
		this.introduction = introduction;
		this.profilePicture = profilePicture;
	}

	public static User create(String name, String profilePicture, SocialAuthProvider provider, String providerSerial) {
		return new User(null, name, null, null, profilePicture, provider, providerSerial);
	}

	public User updateInfo(String name, LocalDate birthday, String introduction, String profilePicture) {
		this.name = name;
		this.birthday = birthday;
		this.introduction = introduction;
		this.profilePicture = profilePicture;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) && Objects.equals(name, user.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
