package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.agent.modellabs.ModelLabsAgent;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.CoverRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class RollingPaperServiceTest {

	@PersistenceContext
	private EntityManager entityManager;

	@MockBean
	private ModelLabsAgent modelLabsAgent;

	@Autowired
	private RollingPaperService rollingPaperService;

	@Test
	void 커버_이미지를_생성하면_롤링페이퍼_커버가_업데이트된다() {
		// given
		Group group = new Group("name");
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture");
		Member member = new Member(user, group);
		RollingPaper rollingPaper = new RollingPaper(null, "title", "content", 1L, member);

		entityManager.persist(user);
		entityManager.persist(group);
		entityManager.persist(member);
		entityManager.persist(rollingPaper);

		String coverImageUrl = "cover_image_url";
		CoverRequest request = new CoverRequest(List.of());

		given(modelLabsAgent.generateCoverWithKeywords(any()))
				.willReturn(List.of(coverImageUrl));

		// when
		rollingPaperService.generateCoverImageAndUpdateRollingPaper(request, rollingPaper.getId());

		// then
		assertThat(rollingPaper.getCoverImageUrl()).isEqualTo(coverImageUrl);
	}

	@Test
	void 롤링페이퍼를_전송할_수_있다() {
		// given
		Group group = new Group("name");
		User user1 = new User("name", LocalDate.now(), "introduction", "profile_picture");
		User user2 = new User("name", LocalDate.now(), "introduction", "profile_picture");
		Member member1 = new Member(user1, group);
		Member member2 = new Member(user2, group);

		entityManager.persist(user1);
		entityManager.persist(user2);
		entityManager.persist(group);
		entityManager.persist(member1);
		entityManager.persist(member2);

		// when
		RollingPaperRequest request = new RollingPaperRequest(group.getId(), "cover_image_url",
				"title", "content", 1L, List.of());

		// then
		assertThatCode(() -> rollingPaperService.sendRollingPaper(user2.getId(), request))
				.doesNotThrowAnyException();
	}

	@Test
	void 롤링페이퍼를_조회할_수_있다() {
		// given
		Group group = new Group("name");
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture");
		Member member = new Member(user, group);
		RollingPaper rollingPaper = new RollingPaper(null, "title", "content", 1L, member);

		entityManager.persist(user);
		entityManager.persist(group);
		entityManager.persist(member);
		entityManager.persist(rollingPaper);

		// when
		RollingPaperResponse response = rollingPaperService.findById(user.getId(), rollingPaper.getId());

		// then
		assertThat(response).usingRecursiveComparison()
				.isEqualTo(RollingPaperResponse.from(rollingPaper));
	}
}
