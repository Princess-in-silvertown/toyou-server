package slvtwn.khu.toyouserver.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CookieAuthArgumentResolver implements HandlerMethodArgumentResolver {
	private final HttpSession httpSession;
	private final SessionUserService sessionUserService;

	public CookieAuthArgumentResolver(HttpSession httpSession, SessionUserService sessionUserService) {
		this.httpSession = httpSession;
		this.sessionUserService = sessionUserService;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserAuthentication.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String sessionId = (String) httpSession.getAttribute("sessionId");
		return sessionUserService.findUserBySessionId(sessionId);
	}
}
