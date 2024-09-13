package slvtwn.khu.toyouserver.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import slvtwn.khu.toyouserver.common.entity.BaseTimeEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RollingPaper extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	private String coverImageUrl;

	private Long themeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "rollingPaper")
	private final List<Sticker> stickers = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	public RollingPaper(String coverImageUrl, String title, String content, Long themeId, Member member) {
		this.coverImageUrl = coverImageUrl;
		this.title = title;
		this.content = content;
		this.themeId = themeId;
		this.member = member;
	}

	public void updateCoverImage(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}
}
