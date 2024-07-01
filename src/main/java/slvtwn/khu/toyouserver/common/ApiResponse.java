package slvtwn.khu.toyouserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"code", "message", "data", "pageInfo"})
public class ApiResponse<T> {

	private final String code;

	private final String message;

	@JsonInclude(Include.NON_NULL)
	private final T data;

	private final PageInfoResponse pageInfo;


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

	ApiResponse(String code, String message, T data, PageInfoResponse pageInfo) {
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

	public static <T> ApiResponse<T> success(SuccessType successType, T data, PageInfoResponse pageInfo) {
		return new ApiResponse<>(successType.getCode(), successType.getMessage(), data, pageInfo);
	}

	public static ApiResponse<?> error(ErrorType errorType) {
		return new ApiResponse<>(errorType.getCode(), errorType.getMessage());
	}

	public static <T> ApiResponse<T> error(ErrorType errorType, T data) {
		return new ApiResponse<>(errorType.getCode(), errorType.getMessage(), data);
	}

	public static ApiResponse<?> error(ErrorType errorType, String message) {
		return new ApiResponse<>(errorType.getCode(), message);
	}

	public static <T> ApiResponse<T> error(ErrorType errorType, String message, T data) {
		return new ApiResponse<>(errorType.getCode(), message, data);
	}

	public static <T> ApiResponse<Exception> error(ErrorType errorType, Exception e) {
		return new ApiResponse<>(errorType.getCode(), errorType.getMessage(), e);
	}

}