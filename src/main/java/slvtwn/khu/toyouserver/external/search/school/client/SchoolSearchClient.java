package slvtwn.khu.toyouserver.external.search.school.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import slvtwn.khu.toyouserver.external.search.school.web.SchoolSearchResult;

@FeignClient(name = "SchoolSearchClient", url = "${external.search.school.url}")
public interface SchoolSearchClient {

	@Value("${external.search.school.key}")
	String getApiKey();

	/**
	 * 커리어넷 API를 호출하여 학교 정보를 검색합니다.
	 *
	 * @param gubun         [필수] 학교 분류 (예: 초등학교, 중학교, 고등학교, 대학교, 대안학교)
	 * @param searchSchulNm [필수] 검색어 (예: 서초)
	 * @param region        지역. 서울특별시 등
	 * @param sch1          학교 유형 (전체, 일반고, 자율고, 특성화고, 특수목적고, 기타)
	 * @param sch2          학교 유형 (전체, 일반고, 자율형사립, 대안교육, 기타)
	 * @param est           설립 유형. 대학교: 국립, 사립, 공립 / 대안학교: 인가, 비인가, 위탁형
	 * @param thisPage      현재 페이지
	 * @param perPage       페이지당 결과 수
	 * @return SchoolSearchResult
	 */
	default SchoolSearchResult searchSchools(
			String gubun,
			String searchSchulNm,
			String region,
			String sch1,
			String sch2,
			String est,
			String thisPage,
			String perPage
	) {
		String SVC_TYPE = "api";
		String SVC_CODE = "school";
		String CONTENT_TYPE = "json";
		return searchSchoolsInternal(getApiKey(), SVC_TYPE, SVC_CODE, CONTENT_TYPE,
				gubun, searchSchulNm, region, sch1, sch2, est, thisPage, perPage);
	}

	@GetMapping("/getOpenApi")
	SchoolSearchResult searchSchoolsInternal(
			@RequestParam("apiKey") String apiKey,
			@RequestParam("svcType") String svcType,
			@RequestParam("svcCode") String svcCode,
			@RequestParam("contentType") String contentType,
			@RequestParam("gubun") String gubun,
			@RequestParam(value = "searchSchulNm", required = false) String searchSchulNm,
			@RequestParam(value = "region", required = false) String region,
			@RequestParam(value = "sch1", required = false) String sch1,
			@RequestParam(value = "sch2", required = false) String sch2,
			@RequestParam(value = "est", required = false) String est,
			@RequestParam(value = "thisPage", required = false) String thisPage,
			@RequestParam(value = "perPage", defaultValue = "10", required = false) String perPage
	);
}