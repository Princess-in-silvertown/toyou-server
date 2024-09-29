package slvtwn.khu.toyouserver.common.authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SecurityWhiteListPaths {
	private static final PathMatcher pathMatcher = new AntPathMatcher();

	/*
	 * 전역 Security Whitelist (모든 사용자에게 인증 없이 접근 허용되는 경로)
	 */
	public static final String[] SECURITY_GLOBAL_WHITELIST = {
			"/error",
			"/auth/login"
	};

	/*
	 * JWT User Authentication Whitelist (JWT 사용자 인증만을 거치지 않고 접근 허용되는 경로)
	 */
	public static final List<String> JWT_USER_AUTHENTICATION_WHITELIST = List.of(
			"/auth/login"
	);

	public static boolean isJWTUserAuthenticationWhitelisted(HttpServletRequest request) {
		String path = request.getRequestURI();
		return JWT_USER_AUTHENTICATION_WHITELIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}