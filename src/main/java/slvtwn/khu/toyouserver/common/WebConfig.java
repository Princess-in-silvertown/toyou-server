package slvtwn.khu.toyouserver.common;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import slvtwn.khu.toyouserver.application.SessionUserService;

public class WebConfig implements WebMvcConfigurer {

	private HttpSession httpSession;

	private SessionUserService sessionUserService;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CookieAuthArgumentResolver(httpSession, sessionUserService));
	}
}
