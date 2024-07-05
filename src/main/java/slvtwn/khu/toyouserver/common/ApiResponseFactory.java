package slvtwn.khu.toyouserver.common;

class ApiResponseFactory {

	static ApiResponse success(SuccessType successType, Object data) {
		return new ApiResponse(successType.getCode(), successType.getMessage(), data);
	}

	static ApiResponse error(ErrorType errorType) {
		return new ApiResponse(errorType.code(), errorType.message());
	}

	static ApiResponse error(ErrorType errorType, Object data) {
		return new ApiResponse(errorType.code(), errorType.message(), data);
	}
}