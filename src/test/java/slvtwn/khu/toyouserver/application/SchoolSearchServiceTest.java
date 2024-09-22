package slvtwn.khu.toyouserver.application;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.domain.School;
import slvtwn.khu.toyouserver.dto.SchoolSearchResponse;

@Transactional
@SpringBootTest
class SchoolSearchServiceTest {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private SchoolSearchService schoolSearchService;

	private School dummySchool;
	private School resultExpectedSchool;
	private School unRelatedSchool;

	@BeforeEach
	void setUp() {
		dummySchool = new School("경희어린이집", "경기도 용인시 기흥구 덕영대로 1732", "경기도", "https://www.khu.ac.kr");
		resultExpectedSchool = new School("경희대학교", "경기도 용인시 기흥구 덕영대로 1732", "경기도", "https://www.khu.ac.kr");
		unRelatedSchool = new School("효섭어린이집", "서울특별시 용산구 이태원동 회나무로", "서울특별시", "https://www.nowhere.com");
		em.persist(dummySchool);
		em.persist(resultExpectedSchool);
		em.persist(unRelatedSchool);
	}

	@DisplayName("이름을 기준으로 학교를 검색한다.")
	@Test
	void searchSchoolWithKeyword() {
		// given
		String keyword = "경희대";

		// when
		var result = schoolSearchService.searchSchoolWithKeyword(keyword);

		// then
		assertThat(result).containsExactly(SchoolSearchResponse.from(resultExpectedSchool));
	}

	@DisplayName("검색어가 포함된 학교가 없을 때 빈 리스트를 반환한다.")
	@Test
	void searchSchoolWithKeyword_noResult() {
		// given
		String keyword = "섭섭어린이집";

		// when
		var result = schoolSearchService.searchSchoolWithKeyword(keyword);

		// then
		assertThat(result).isEmpty();
	}

	@DisplayName("검색어가 포함된 학교가 여러 개일 때 모두 반환한다.")
	@Test
	void searchSchoolWithKeyword_multipleResults() {
		// given
		String keyword = "어린이집";

		// when
		var result = schoolSearchService.searchSchoolWithKeyword(keyword);

		// then
		List<SchoolSearchResponse> expected = List.of(
				SchoolSearchResponse.from(dummySchool),
				SchoolSearchResponse.from(unRelatedSchool));
		assertThat(result).isEqualTo(expected);
	}
}