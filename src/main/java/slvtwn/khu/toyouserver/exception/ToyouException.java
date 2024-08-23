package slvtwn.khu.toyouserver.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import slvtwn.khu.toyouserver.common.response.ErrorType;

@RequiredArgsConstructor
@Getter
public class ToyouException extends RuntimeException {

	private final ErrorType errorType;
}
