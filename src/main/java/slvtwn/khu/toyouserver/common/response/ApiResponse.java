package slvtwn.khu.toyouserver.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"timestamp", "code", "message", "data"})
public class ApiResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss", timezone = "Asia/Seoul")
	@JsonProperty("timestamp")
	private LocalDateTime timestamp = LocalDateTime.now();

	private final String code;

	private final String message;

	@JsonInclude(Include.NON_NULL)
	private final Object data;

	ApiResponse(String code, String message) {
		this(code, message, null);
	}

	ApiResponse(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
