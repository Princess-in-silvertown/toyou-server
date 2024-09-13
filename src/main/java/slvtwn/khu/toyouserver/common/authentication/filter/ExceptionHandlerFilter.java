package slvtwn.khu.toyouserver.common.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import slvtwn.khu.toyouserver.common.response.ResponseType;
import slvtwn.khu.toyouserver.exception.ToyouException;
import slvtwn.khu.toyouserver.exception.UnauthorizedException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (UnauthorizedException e) {
			handleUnauthorizedException(response, e);
		} catch (Exception ee) {
			handleException(response, ee);
		}
	}

	private void handleUnauthorizedException(
			HttpServletResponse response,
			Exception e) throws IOException {
		UnauthorizedException ue = (UnauthorizedException) e;
		ResponseType authErrorCode = ue.getResponseType();
		HttpStatusCode httpStatus = authErrorCode.getHttpStatusCode();
		setResponse(response, httpStatus, authErrorCode);
	}

	private void handleException(
			HttpServletResponse response,
			Exception e) throws IOException {
		setResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, ResponseType.INTERNAL_SERVER_ERROR);
	}

	private void setResponse(
			HttpServletResponse response,
			HttpStatusCode httpStatus,
			ResponseType authErrorCode) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("utf-8");
		response.setStatus(httpStatus.value());
		PrintWriter writer = response.getWriter();
		writer.write(objectMapper.writeValueAsString(new ToyouException(authErrorCode)));
	}
}