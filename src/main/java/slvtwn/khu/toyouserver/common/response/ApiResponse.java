package slvtwn.khu.toyouserver.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;
import slvtwn.khu.toyouserver.dto.PageInfo;

@Getter
@JsonPropertyOrder({"timestamp", "code", "message", "data"})
public class ApiResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
    private final String code;

    @JsonInclude(Include.NON_NULL)
    private final String message;

    @JsonInclude(Include.NON_NULL)
    private final Object data;
    @JsonInclude(Include.NON_NULL)
    private final PageInfo pageInfo;

    public static ApiResponse error(String code, String message) {
        return new ApiResponse(code, message, null, null);
    }

    public static ApiResponse success(String code, Object data) {
        return new ApiResponse(code, null, data, null);
    }

    public static ApiResponse success(String code, Object data, PageInfo pageInfo) {
        return new ApiResponse(code, null, data, pageInfo);
    }

    private ApiResponse(String code, String message, Object data, PageInfo pageInfo) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
