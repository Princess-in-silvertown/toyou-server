package slvtwn.khu.toyouserver.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slvtwn.khu.toyouserver.common.entity.BaseTimeEntity;

@Entity
@Table(name = "`groups`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Group extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	private String name;

	private String address;
	private String region;
	private String homepageUrl;

	public Group(String name) {
		this(name, null, null, null);
	}

	public Group(String name, String address, String region, String homepageUrl) {
		this.name = name;
		this.address = address;
		this.region = region;
		this.homepageUrl = homepageUrl;
	}
}
