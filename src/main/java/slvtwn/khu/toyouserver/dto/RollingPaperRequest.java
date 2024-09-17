package slvtwn.khu.toyouserver.dto;

import java.util.List;

public record RollingPaperRequest(Long groupId, String coverImageUrl, String title, String content, Long themeId,
                                  List<StickerRequest> stickers) {
}
