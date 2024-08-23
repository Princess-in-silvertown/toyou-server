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
	public ResponseEntity<Object> handleToyouException(@NonNull final ToyouException e) {
		return createResponse(e.getErrorType());
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal
			(@NonNull final Exception ex,
			 @NonNull final Object body,
			 @NonNull final HttpHeaders headers,
			 @NonNull final HttpStatusCode statusCode,
			 @NonNull final WebRequest request) {
		return createResponse(INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleNoResourceFoundException(
			@NonNull final NoResourceFoundException ex,
			@NonNull final HttpHeaders headers,
			@NonNull final HttpStatusCode status,
			@NonNull final WebRequest request) {
		return createResponse(NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleErrorResponseException(
			@NonNull final ErrorResponseException ex,
			@NonNull final HttpHeaders headers,
			@NonNull final HttpStatusCode status,
			@NonNull final WebRequest request) {
		ApiResponse response = new ApiResponse(BAD_REQUEST.code(), ex.getBody().getTitle());
		return createResponse(BAD_REQUEST.httpStatusCode(), response);
	}

	private ResponseEntity<Object> createResponse(final ErrorType errorType) {
		return createResponse(
				errorType.httpStatusCode(),
				new ApiResponse(errorType.code(), errorType.message())
		);
	}

	private ResponseEntity<Object> createResponse(
			final HttpStatusCode httpStatus,
			final ApiResponse response
	) {
		return ResponseEntity.status(httpStatus)
				.body(response);
	}
}
