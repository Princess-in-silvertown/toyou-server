package slvtwn.khu.toyouserver.common.configuration;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import slvtwn.khu.toyouserver.application.SessionService;
import slvtwn.khu.toyouserver.common.authentication.CookieAuthArgumentResolver;

@Configuration
@AllArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private SessionService sessionService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CookieAuthArgumentResolver(sessionService));
    }
}
