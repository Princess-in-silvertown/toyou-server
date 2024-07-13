package slvtwn.khu.toyouserver.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorType {

	// 400 ~ 499 (요청 오류)
	RESPONSE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "TYU-400", "응답 형식 오류"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "TYU-400", "잘못된 요청입니다."),
	NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-404", "요청한 자원을 찾을 수 없습니다."),
	GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-4041", "그룹을 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-4042", "사용자를 찾을 수 없습니다."),

	// 500 ~ 599 (서버 오류)
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TYU-500", "서버 내부 오류");


	ErrorType(
			final HttpStatusCode httpStatusCode,
			final String code,
			final String message
	) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
	}

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	public HttpStatusCode httpStatusCode() {
		return httpStatusCode;
	}

	public String code() {
		return code;
	}

	public String message() {
		return message;
	}

}
