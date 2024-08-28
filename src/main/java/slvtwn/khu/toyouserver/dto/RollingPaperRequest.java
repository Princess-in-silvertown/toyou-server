package slvtwn.khu.toyouserver.dto;

import java.util.List;

public record RollingPaperRequest(String coverImageUrl, String title, String content, Long themeId,
                                  List<StickerRequest> stickers) {

}
