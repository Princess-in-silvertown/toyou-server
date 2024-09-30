package slvtwn.khu.toyouserver.dto;

import org.springframework.data.domain.Slice;

public record CursorPageInfo(Long nextCursorId, int numberOfElements, boolean hasNext, int currentPage,
                             boolean isFirst, boolean isLast) {
	public static CursorPageInfo from(Slice<?> data, Long lastCursor) {
		return new CursorPageInfo(lastCursor, data.getNumberOfElements(), data.hasNext(), data.getNumber(),
				data.isFirst(), data.isLast());
	}
}