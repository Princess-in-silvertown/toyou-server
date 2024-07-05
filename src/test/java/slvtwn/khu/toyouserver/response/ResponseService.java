package slvtwn.khu.toyouserver.response;

import org.springframework.stereotype.Service;
import slvtwn.khu.toyouserver.common.PageInfoResponse;

@Service
public class ResponseService {

	public PageableDto health() {
		PageInfoResponse pageInfo = PageInfoResponse.of(1, 1, 1);
		return new PageableDto(1L, "health", pageInfo);
	}
}
