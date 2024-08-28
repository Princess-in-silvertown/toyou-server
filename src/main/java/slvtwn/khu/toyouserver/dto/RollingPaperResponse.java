package slvtwn.khu.toyouserver.dto;

import java.util.List;
import slvtwn.khu.toyouserver.domain.RollingPaper;
import slvtwn.khu.toyouserver.domain.Sticker;

public record RollingPaperResponse(String coverImageUrl, String title, String content, Long themeId,
                                   List<StickerResponse> stickers) {

    public static RollingPaperResponse from(RollingPaper rollingPaper) {
        List<StickerResponse> stickerResponses = rollingPaper.getStickers().stream()
                .map(StickerResponse::from)
                .toList();

        return new RollingPaperResponse(rollingPaper.getCoverImageUrl(), rollingPaper.getTitle(),
                rollingPaper.getContent(), rollingPaper.getThemeId(), stickerResponses);
    }
}
