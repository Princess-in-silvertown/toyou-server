package slvtwn.khu.toyouserver.common;

// TODO: 효율적인 예외처리 Wrapping 과정에 대해 고민
public record ToyouResponse<T>(String code, T data) {

    public static <T> ToyouResponse<T> success(T data) {
        return new ToyouResponse<>("SUCCESS", data);
    }
}
