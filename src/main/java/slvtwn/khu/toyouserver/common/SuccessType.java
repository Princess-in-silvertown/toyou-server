package slvtwn.khu.toyouserver.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessType {

	// 200 (OK)
	OK("TYU200", "success");

	private final String code;
	private final String message;
}
