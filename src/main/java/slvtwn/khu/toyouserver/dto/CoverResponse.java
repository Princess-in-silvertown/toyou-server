package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.Cover;

public record CoverResponse(String coverImageUrl) {

    public static CoverResponse from(Cover cover) {
        return new CoverResponse(cover.getImageUrl());
    }
}
