package slvtwn.khu.toyouserver.common.response;

import slvtwn.khu.toyouserver.dto.PageInfo;

public record ToyouResponse<T>(T data, PageInfo pageInfo) {

    public static <T> ToyouResponse<T> from(T data) {
        return of(data, null);
    }

    public static <T> ToyouResponse<T> of(T data, PageInfo pageInfo) {
        return new ToyouResponse<>(data, pageInfo);
    }
}
