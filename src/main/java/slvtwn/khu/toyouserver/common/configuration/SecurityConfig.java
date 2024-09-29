package slvtwn.khu.toyouserver.common.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import slvtwn.khu.toyouserver.common.authentication.JwtAuthenticationEntryPoint;
import slvtwn.khu.toyouserver.common.authentication.SecurityWhiteListPaths;
import slvtwn.khu.toyouserver.common.authentication.filter.ExceptionHandlerFilter;
import slvtwn.khu.toyouserver.common.authentication.filter.JwtAuthenticationFilter;
import slvtwn.khu.toyouserver.common.authentication.jwt.JwtProvider;
import slvtwn.khu.toyouserver.common.authentication.jwt.JwtValidator;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Value("${server.domain}")
	private String serverDomain;

	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtValidator jwtValidator;
	private final JwtProvider jwtProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
				.sessionManagement(sessionManagementConfigurer ->
						sessionManagementConfigurer
								.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
						authorizationManagerRequestMatcherRegistry.requestMatchers(
										SecurityWhiteListPaths.SECURITY_GLOBAL_WHITELIST).permitAll()
								.anyRequest().authenticated())
				.exceptionHandling(exceptionHandlingConfigurer ->
						exceptionHandlingConfigurer
								.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.addFilterBefore(new JwtAuthenticationFilter(jwtValidator, jwtProvider),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new ExceptionHandlerFilter(), JwtAuthenticationFilter.class)
				.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.addAllowedOrigin("http://localhost:8080");
		configuration.addAllowedOrigin(serverDomain);
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.addExposedHeader("Authorization");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}