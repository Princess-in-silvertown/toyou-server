package slvtwn.khu.toyouserver.common.authentication;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SecurityWhiteListPaths {
	private static final PathMatcher pathMatcher = new AntPathMatcher();
	public static final String[] WHITE_LIST = {"/**"};

	public static final List<String> FILTER_WHITE_LIST = List.of("/**");

	public static boolean isWhitelisted(HttpServletRequest request) {
		String path = request.getRequestURI();
		return FILTER_WHITE_LIST.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
	}
}
