package slvtwn.khu.toyouserver.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slvtwn.khu.toyouserver.dto.SchoolSearchResponse;
import slvtwn.khu.toyouserver.persistance.SchoolRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class SchoolSearchService {

	private final SchoolRepository schoolRepository;

	@Transactional
	public List<SchoolSearchResponse> searchSchoolWithKeyword(String keyword) {
		return schoolRepository.findByNameContaining(keyword).stream()
				.map(SchoolSearchResponse::from)
				.toList();
	}
}