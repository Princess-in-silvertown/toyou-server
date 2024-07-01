package slvtwn.khu.toyouserver.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PageInfoResponse {
	private final int currentPage;

	private final int totalPage;

	private final int totalElements;

	public static PageInfoResponse of(int currentPage, int totalPage, int totalElements) {
		return new PageInfoResponse(currentPage, totalPage, totalElements);
	}
}
