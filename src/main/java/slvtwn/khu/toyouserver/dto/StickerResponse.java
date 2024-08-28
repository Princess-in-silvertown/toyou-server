package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.Sticker;

public record StickerResponse(Long id, String imageUrl, Integer x, Integer y,
                              Double rotate, Double scale, String side) {

    public static StickerResponse from(Sticker sticker) {
        return new StickerResponse(sticker.getId(), sticker.getImageUrl(), sticker.getX(), sticker.getY(),
                sticker.getRotate(), sticker.getScale(), sticker.getSide().name());
    }
}
