package slvtwn.khu.toyouserver.dto;

import org.springframework.data.domain.Slice;

public record CursorPageInfo(Long nextCursorId, int numberOfElements, boolean hasNext) {
	public static CursorPageInfo from(Slice<?> data, Long nextCursorId, int numberOfElements, boolean hasNext) {
		return new CursorPageInfo(nextCursorId, data.getNumberOfElements(), data.hasNext());
	}
}