package slvtwn.khu.toyouserver.response;

import slvtwn.khu.toyouserver.common.PageInfoResponse;

public record PageableDto(
		Long id,
		String name,
		PageInfoResponse pageInfo) {
}
