package slvtwn.khu.toyouserver.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ResponseType {

	OK(HttpStatus.OK, "TYU-200", "응답 성공"),

	// 400 Bad Request
	RESPONSE_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "TYU-400", "응답 형식 오류"),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "TYU-400", "잘못된 요청입니다."),
	NOT_SUPPORTED_AUTH_PROVIDER(HttpStatus.BAD_REQUEST, "TYU-4001", "지원하지 않는 로그인 플랫폼입니다."),

	// 401 Unauthorized
	UNAUTHORIZED_USER_ACCESS(HttpStatus.UNAUTHORIZED, "TYU-4012", "인증되지 않은 사용자 접근입니다."),

	// 403 Forbidden

	// 404 Not Found
	NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-404", "요청한 자원을 찾을 수 없습니다."),
	GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-4041", "그룹을 찾을 수 없습니다."),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "TYU-4042", "사용자를 찾을 수 없습니다."),

	// 500 Internal Server Error
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TYU-500", "서버 내부 오류");


	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;

	ResponseType(HttpStatusCode httpStatusCode, String code, String message) {
		this.httpStatusCode = httpStatusCode;
		this.code = code;
		this.message = message;
	}
}
