package slvtwn.khu.toyouserver.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import slvtwn.khu.toyouserver.application.SchoolSearchService;
import slvtwn.khu.toyouserver.common.response.ToyouResponse;
import slvtwn.khu.toyouserver.dto.SchoolSearchResponse;

@RequiredArgsConstructor
@RestController
public class SchoolController {

	private SchoolSearchService schoolSearchService;

	@GetMapping("/schools/search")
	public ToyouResponse<List<SchoolSearchResponse>> searchSchools(@RequestParam String keyword) {
		return ToyouResponse.from(schoolSearchService.searchSchoolWithKeyword(keyword));
	}
}