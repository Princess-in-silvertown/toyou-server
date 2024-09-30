package slvtwn.khu.toyouserver.dto;

import org.springframework.data.domain.Slice;

public record CursorPageInfo(Long nextCursorId, int numberOfElements, boolean hasNext) {
	public static CursorPageInfo from(Slice<?> data, Long lastCursor) {
		return new CursorPageInfo(lastCursor, data.getNumberOfElements(), data.hasNext());
	}
}