package slvtwn.khu.toyouserver.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "schools")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class School {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	private String name;
	private String address;
	private String region;
	private String homepageUrl;

	public School(String name, String address, String region, String homepageUrl) {
		this.name = name;
		this.address = address;
		this.region = region;
		this.homepageUrl = homepageUrl;
	}
}