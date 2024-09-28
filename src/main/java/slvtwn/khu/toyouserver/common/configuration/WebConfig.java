package slvtwn.khu.toyouserver.common.configuration;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import slvtwn.khu.toyouserver.common.authentication.JWTAuthArgumentResolver;


@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final JWTAuthArgumentResolver jwtAuthArgumentResolver;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(jwtAuthArgumentResolver);
	}
}