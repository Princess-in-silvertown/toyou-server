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

	public static boolean isWhitelisted(HttpServletRequest request) {
		String path = request.getRequestURI();
		return FILTER_WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}