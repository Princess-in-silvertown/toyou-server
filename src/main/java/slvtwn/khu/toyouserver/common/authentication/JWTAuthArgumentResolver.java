package slvtwn.khu.toyouserver.common.authentication;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class JWTAuthArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthMemberAnnotation = parameter.hasParameterAnnotation(UserAuthentication.class);
		boolean isUserEntityType = parameter.getParameterType().equals(Long.class);
		return hasAuthMemberAnnotation && isUserEntityType;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
	                              NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		return SecurityContextHolder.getContext()
				.getAuthentication()
				.getPrincipal();
	}
}