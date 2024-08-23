package slvtwn.khu.toyouserver.common.response;


import static slvtwn.khu.toyouserver.common.response.ApiResponseFactory.error;
import static slvtwn.khu.toyouserver.common.response.ApiResponseFactory.success;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "slvtwn.khu.toyouserver")
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	                              Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		HttpServletResponse servletResponse =
				((ServletServerHttpResponse) response).getServletResponse();

		HttpStatus resolve = HttpStatus.resolve(servletResponse.getStatus());

		if (!(body instanceof ApiResponse)) {
			assert resolve != null;
			return createResponseByHttpStatus(resolve, body);
		}
		return body;
	}

	private Object createResponseByHttpStatus(HttpStatus status, Object body) {
		if (status.is2xxSuccessful()) {
			return success(SuccessType.OK, body);
		} else if (status.is4xxClientError()) {
			return error(ErrorType.BAD_REQUEST);
		} else if (status.is5xxServerError()) {
			return error(ErrorType.INTERNAL_SERVER_ERROR);
		}
		return error(ErrorType.RESPONSE_FORMAT_ERROR, body);
	}
}
