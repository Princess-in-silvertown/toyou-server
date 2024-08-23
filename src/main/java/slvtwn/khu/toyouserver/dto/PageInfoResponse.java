package slvtwn.khu.toyouserver.dto;

public record PageInfoResponse(int currentPage, int totalPage, int totalElements) {

    public static PageInfoResponse of(int currentPage, int totalPage, int totalElements) {
        return new PageInfoResponse(currentPage, totalPage, totalElements);
    }
}
