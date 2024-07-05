package slvtwn.khu.toyouserver.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {

	// 200 (OK)
	OK(HttpStatus.OK, "TYU-200", "응답 성공");

	private final HttpStatusCode httpStatusCode;
	private final String code;
	private final String message;
}
