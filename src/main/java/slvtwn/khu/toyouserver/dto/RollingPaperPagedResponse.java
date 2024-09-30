package slvtwn.khu.toyouserver.dto;

import java.util.List;

public record RollingPaperPagedResponse(CursorPageInfo cursorPageInfo, List<RollingPaperResponse> contents) {

	public static RollingPaperPagedResponse from(CursorPageInfo cursorPageInfo, List<RollingPaperResponse> contents) {
		return new RollingPaperPagedResponse(cursorPageInfo, contents);
	}
}
