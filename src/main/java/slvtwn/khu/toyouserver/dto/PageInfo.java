package slvtwn.khu.toyouserver.dto;

public record PageInfo(int currentPage, int totalPage, int totalElements) {

    public static PageInfo of(int currentPage, int totalPage, int totalElements) {
        return new PageInfo(currentPage, totalPage, totalElements);
    }
}
