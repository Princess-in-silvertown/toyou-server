package slvtwn.khu.toyouserver.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {

	private final String code;

	private final String message;

	private final T data;

	private final Object pageInfo;

	ApiResponse(String code, String message) {
		this.code = code;
		this.message = message;
		this.data = null;
		this.pageInfo = null;
	}

	ApiResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.pageInfo = null;

	}

	ApiResponse(String code, String message, T data, Object pageInfo) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.pageInfo = pageInfo;
	}

	public static <T> ApiResponse<T> success(SuccessType successType) {
		return new ApiResponse<>(successType.getCode(), successType.getMessage());
	}

	public static <T> ApiResponse<T> success(SuccessType successType, T data) {
		return new ApiResponse<>(successType.getCode(), successType.getMessage(), data);
	}

	public static <T> ApiResponse<T> success(SuccessType successType, T data, Object pageInfo) {
		return new ApiResponse<>(successType.getCode(), successType.getMessage(), data, pageInfo);
	}

	public static <T> ApiResponse<T> error(ErrorType errorType) {
		return new ApiResponse<>(errorType.getCode(), errorType.getMessage());
	}
}