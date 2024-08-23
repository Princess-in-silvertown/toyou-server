package slvtwn.khu.toyouserver.common.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import slvtwn.khu.toyouserver.application.SessionService;

public class CookieAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionService sessionService;

    public CookieAuthArgumentResolver(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserAuthentication.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        Long sessionId = Long.parseLong(httpServletRequest.getHeader("sessionId"));
        return sessionService.findById(sessionId);
    }
}
