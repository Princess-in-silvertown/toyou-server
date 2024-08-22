package slvtwn.khu.toyouserver.common;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import slvtwn.khu.toyouserver.application.SessionService;

public class CookieAuthArgumentResolver implements HandlerMethodArgumentResolver {
	private final HttpSession httpSession;
	private final SessionService sessionService;

	public CookieAuthArgumentResolver(HttpSession httpSession, SessionService sessionService) {
		this.httpSession = httpSession;
		this.sessionService = sessionService;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return UserAuthentication.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		Long sessionId = Long.parseLong((String) httpSession.getAttribute("sessionId"));
		return sessionService.findById(sessionId);
	}
}
