package slvtwn.khu.toyouserver.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import slvtwn.khu.toyouserver.dto.PageInfo;

@RestControllerAdvice
@AllArgsConstructor
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        HttpServletResponse servletResponse =
                ((ServletServerHttpResponse) response).getServletResponse();

        Map<String, Object> map = objectMapper.convertValue(body, Map.class);
        PageInfo pageInfo = (PageInfo) map.get("pageInfo");
        Object data = map.get("body");

        HttpStatus httpStatus = HttpStatus.resolve(servletResponse.getStatus());
        return createResponseByHttpStatus(httpStatus, data, pageInfo);
    }

    private Object createResponseByHttpStatus(HttpStatus status, Object data, PageInfo pageInfo) {
        if (status.is2xxSuccessful()) {
            return ApiResponse.success(ResponseType.OK.getCode(), data, pageInfo);
        } else if (status.is4xxClientError()) {
            return ApiResponse.error(ResponseType.BAD_REQUEST.getCode(), ResponseType.BAD_REQUEST.getMessage());
        }
        return ApiResponse.error(ResponseType.INTERNAL_SERVER_ERROR.getCode(), ResponseType.BAD_REQUEST.getMessage());
    }
}
