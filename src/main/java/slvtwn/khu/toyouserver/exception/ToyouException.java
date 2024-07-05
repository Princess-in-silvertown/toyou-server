package slvtwn.khu.toyouserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import slvtwn.khu.toyouserver.common.ErrorType;

@RequiredArgsConstructor
@Getter
public final class ToyouException extends RuntimeException {
	private final ErrorType errorType;

	public String code() {
		return errorType.code();
	}

	public String message() {
		return errorType.message();
	}
}
