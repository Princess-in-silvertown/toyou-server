package slvtwn.khu.toyouserver.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorType {

	// 404 (Not Found)
	NOT_FOUND("TYU404", "Not Found");

	private final String code;

	private final String message;
}
