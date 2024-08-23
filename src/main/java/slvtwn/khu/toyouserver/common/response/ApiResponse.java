package slvtwn.khu.toyouserver.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;
import slvtwn.khu.toyouserver.dto.PageInfoResponse;

@Getter
@JsonPropertyOrder({"timestamp", "code", "message", "data"})
public class ApiResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
    private final String code;

    @JsonInclude(Include.NON_NULL)
    private final Object data;
    private final PageInfoResponse pageInfo;

    public ApiResponse(String code) {
        this(code, null, null);
    }

    public ApiResponse(String code, Object data) {
        this(code, data, null);
    }

    public ApiResponse(String code, Object data, PageInfoResponse pageInfo) {
        this.code = code;
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
