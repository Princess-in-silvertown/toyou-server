package slvtwn.khu.toyouserver.common.response;

import static slvtwn.khu.toyouserver.common.response.ErrorType.BAD_REQUEST;
import static slvtwn.khu.toyouserver.common.response.ErrorType.INTERNAL_SERVER_ERROR;
import static slvtwn.khu.toyouserver.common.response.ErrorType.NOT_FOUND;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import slvtwn.khu.toyouserver.exception.ToyouException;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ToyouException.class)
	public ResponseEntity<Object> handleToyouException(@NonNull ToyouException e) {
		return createResponse(e.getErrorType());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal
			(@NonNull Exception ex,
			 @NonNull Object body,
			 @NonNull HttpHeaders headers,
			 @NonNull HttpStatusCode statusCode,
			 @NonNull WebRequest request) {
		return createResponse(INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(
			@NonNull NoResourceFoundException ex,
			@NonNull HttpHeaders headers,
			@NonNull HttpStatusCode status,
			@NonNull WebRequest request) {
		return createResponse(NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleErrorResponseException(
			@NonNull ErrorResponseException ex,
			@NonNull HttpHeaders headers,
			@NonNull HttpStatusCode status,
			@NonNull WebRequest request) {
		return createResponse(BAD_REQUEST);
	}

	private ResponseEntity<Object> createResponse(ErrorType errorType) {
		return ResponseEntity.status(errorType.httpStatusCode())
				.body(ApiResponse.error(errorType.code(), errorType.message()));
	}
}
