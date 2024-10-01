package slvtwn.khu.toyouserver.dto;

import java.util.List;
import slvtwn.khu.toyouserver.domain.RollingPaper;

public record RollingPaperResponse(Long id, String coverImageUrl, String title, String content, Long themeId,
                                   List<StickerResponse> stickers, String name, String profileImageUrl) {

	public static RollingPaperResponse from(RollingPaper rollingPaper) {
		List<StickerResponse> stickerResponses = rollingPaper.getStickers().stream()
				.map(StickerResponse::from)
				.toList();

		return new RollingPaperResponse(rollingPaper.getId(), rollingPaper.getCoverImageUrl(), rollingPaper.getTitle(),
				rollingPaper.getContent(), rollingPaper.getThemeId(), stickerResponses,
				rollingPaper.getSenderSnapshot().getName(), rollingPaper.getSenderSnapshot().getProfileImageUrl());
	}
}
