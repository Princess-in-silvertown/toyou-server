package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.Group;
import slvtwn.khu.toyouserver.domain.Member;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.User;
import slvtwn.khu.toyouserver.dto.RollingPaperPagedResponse;
import slvtwn.khu.toyouserver.dto.RollingPaperRequest;
import slvtwn.khu.toyouserver.dto.RollingPaperResponse;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Transactional
@SpringBootTest
class RollingPaperServiceTest {

	@PersistenceContext
	private EntityManager entityManager;

//	@MockBean
//	private ModelLabsAgent modelLabsAgent;

	@Autowired
	private RollingPaperService rollingPaperService;

//	@Test
//	void 커버_이미지를_생성하면_롤링페이퍼_커버가_업데이트된다() {
//		// given
//		Group group = new Group("name");
//		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
//		Member member = new Member(user, group);
//		RollingPaper rollingPaper = new RollingPaper(null, "title", "content", 1L, member);
//
//		entityManager.persist(user);
//		entityManager.persist(group);
//		entityManager.persist(member);
//		entityManager.persist(rollingPaper);
//
//		String coverImageUrl = "cover_image_url";
//		CoverRequest request = new CoverRequest(List.of());
//
//		given(modelLabsAgent.generateCoverWithKeywords(any()))
//				.willReturn(List.of(coverImageUrl));
//
//		// when
//		rollingPaperService.generateCoverImageAndUpdateRollingPaper(request, rollingPaper.getId());
//
//		// then
//		assertThat(rollingPaper.getCoverImageUrl()).isEqualTo(coverImageUrl);
//	}

	@Test
	void 롤링페이퍼를_전송할_수_있다() {
		// given
		Group group = new Group("name");
		User user1 = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		User user2 = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
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
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
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

	@Test
	void 사용자는_자신이_수신한_롤링페이퍼를_조회할_수_있다() {
		// given
		Group group = new Group("name");
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		Member member = new Member(user, group);
		RollingPaper rollingPaper = new RollingPaper(null, "title", "content", 1L, member);

		entityManager.persist(user);
		entityManager.persist(group);
		entityManager.persist(member);
		entityManager.persist(rollingPaper);

		// when
		RollingPaperPagedResponse response = rollingPaperService.findReceivedRollingPapers(user.getId(), group.getId(),
				0L, 10);

		// then
		assertThat(response.contents()).usingRecursiveComparison()
				.isEqualTo(List.of(RollingPaperResponse.from(rollingPaper)));
	}

	@Test
	void 수신한_롤링페이퍼_조회는_그룹_식별자가_null인_경우_유저가_수신한_모든_롤링페이퍼를_반환한다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		Group group1 = new Group("group1");
		Group group2 = new Group("group2");
		Member member1 = new Member(user, group1);
		Member member2 = new Member(user, group2);
		RollingPaper rollingPaper = new RollingPaper(null, "title", "rollingPaper", 1L, member1);
		RollingPaper anotherRollingPaper = new RollingPaper(null, "title", "anotherRollingPaper", 1L, member2);

		entityManager.persist(user);
		entityManager.persist(group1);
		entityManager.persist(group2);
		entityManager.persist(member1);
		entityManager.persist(member2);
		entityManager.persist(rollingPaper);
		entityManager.persist(anotherRollingPaper);

		// when
		RollingPaperPagedResponse responseWithoutGroupId = rollingPaperService.findReceivedRollingPapers(
				user.getId(),
				null,
				0L, 10);

		// then
		assertThat(responseWithoutGroupId.contents()).usingRecursiveComparison()
				.isEqualTo(List.of(
						RollingPaperResponse.from(anotherRollingPaper),
						RollingPaperResponse.from(rollingPaper)));

	}

	@Test
	void 수신한_롤링페이퍼_조회는_그룹_식별자가_존재할_경우_해당_그룹의_롤링페이퍼만_반환한다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		Group group1 = new Group("group1");
		Group group2 = new Group("group2");
		Member member1 = new Member(user, group1);
		Member member2 = new Member(user, group2);
		RollingPaper rollingPaper = new RollingPaper(null, "title", "rollingPaper", 1L, member1);
		RollingPaper anotherRollingPaper = new RollingPaper(null, "title", "anotherRollingPaper", 1L, member2);

		entityManager.persist(user);
		entityManager.persist(group1);
		entityManager.persist(group2);
		entityManager.persist(member1);
		entityManager.persist(member2);
		entityManager.persist(rollingPaper);
		entityManager.persist(anotherRollingPaper);

		// when
		RollingPaperPagedResponse responseWithGroupId = rollingPaperService.findReceivedRollingPapers(
				user.getId(),
				group1.getId(),
				0L, 10);

		// then
		assertThat(responseWithGroupId.contents()).usingRecursiveComparison()
				.isEqualTo(List.of(RollingPaperResponse.from(rollingPaper)));
	}

	@Test
	void 수신한_롤링페이퍼_조회는_다른_그룹의_커서를_사용해도_해당_그룹의_편지만_조회된다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		Group group1 = new Group("group1");
		Group group2 = new Group("group2");
		Member member1 = new Member(user, group1);
		Member member2 = new Member(user, group2);

		RollingPaper rollingPaper1 = new RollingPaper(null, "title1", "rollingPaper1", 1L, member1);
		RollingPaper rollingPaper2 = new RollingPaper(null, "title2", "rollingPaper2", 1L, member1);
		RollingPaper rollingPaper3 = new RollingPaper(null, "title3", "rollingPaper3", 1L, member2);

		entityManager.persist(user);
		entityManager.persist(group1);
		entityManager.persist(group2);
		entityManager.persist(member1);
		entityManager.persist(member2);
		entityManager.persist(rollingPaper1);
		entityManager.persist(rollingPaper2);
		entityManager.persist(rollingPaper3);

		// when
		RollingPaperPagedResponse response = rollingPaperService.findReceivedRollingPapers(
				user.getId(),
				group1.getId(),
				0L, 10);

		// then
		List<RollingPaperResponse> expectedResponse = List.of(
				RollingPaperResponse.from(rollingPaper2),
				RollingPaperResponse.from(rollingPaper1)
		);

		assertThat(response.contents()).usingRecursiveComparison()
				.isEqualTo(expectedResponse);
	}

	@Test
	void 수신한_롤링페이퍼_조회는_생성된_시간이_최신인_롤링페이퍼부터_조회된다() {
		// given
		User user = new User("name", LocalDate.now(), "introduction", "profile_picture", null);
		Group group = new Group("group");
		Member member = new Member(user, group);

		RollingPaper rollingPaper1 = new RollingPaper(null, "title1", "rollingPaper1", 1L, member);
		RollingPaper rollingPaper2 = new RollingPaper(null, "title2", "rollingPaper2", 1L, member);
		RollingPaper rollingPaper3 = new RollingPaper(null, "title3", "rollingPaper3", 1L, member);

		entityManager.persist(user);
		entityManager.persist(group);
		entityManager.persist(member);
		entityManager.persist(rollingPaper1);
		entityManager.persist(rollingPaper2);
		entityManager.persist(rollingPaper3);

		// when
		RollingPaperPagedResponse response = rollingPaperService.findReceivedRollingPapers(
				user.getId(),
				group.getId(),
				0L, 10);

		// then
		List<RollingPaperResponse> expectedResponse = List.of(
				RollingPaperResponse.from(rollingPaper3),
				RollingPaperResponse.from(rollingPaper2),
				RollingPaperResponse.from(rollingPaper1)
		);

		assertThat(response.contents()).usingRecursiveComparison()
				.isEqualTo(expectedResponse);
	}
}
