package slvtwn.khu.toyouserver.external.search.school.web;

import java.util.List;

public record CareerNetSchoolResult(
		SchoolDataResults dataSearch
) {
	/**
	 * @param schoolName     학교명
	 * @param seq            학교 고유번호 (unique)
	 * @param adres          학교 주소 (예: 서울특별시 강남구 학동로 25)
	 * @param region         지역 (예: 서울특별시)
	 * @param totalCount     검색 결과 총 개수
	 * @param estType        설립 유형 (예: 공립)
	 * @param collegeInfoUrl 대학정보 URL (대학교 검색 시에만 존재)
	 * @param link           학교 상세정보 URL
	 */
	public record Content(
			String schoolName,
			String seq,
			String adres,
			String region,
			String totalCount,
			String estType,
			String collegeInfoUrl,
			String link
	) {
	}

	/**
	 * @param content 학교별 검색 결과 정보 리스트
	 */
	public record SchoolDataResults(List<Content> content) {}
}