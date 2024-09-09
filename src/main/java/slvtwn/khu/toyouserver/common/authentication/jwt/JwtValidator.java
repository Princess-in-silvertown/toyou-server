package slvtwn.khu.toyouserver.common.authentication.jwt;

import static slvtwn.khu.toyouserver.common.authentication.filter.JwtAuthenticationFilter.BEARER;
import static slvtwn.khu.toyouserver.common.response.ResponseType.EXPIRED_ACCESS_TOKEN;
import static slvtwn.khu.toyouserver.common.response.ResponseType.EXPIRED_REFRESH_TOKEN;
import static slvtwn.khu.toyouserver.common.response.ResponseType.INVALID_ACCESS_TOKEN_VALUE;
import static slvtwn.khu.toyouserver.common.response.ResponseType.INVALID_REFRESH_TOKEN_VALUE;
import static slvtwn.khu.toyouserver.common.response.ResponseType.MISMATCH_REFRESH_TOKEN;
import static slvtwn.khu.toyouserver.common.response.ResponseType.MISSING_BEARER_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import slvtwn.khu.toyouserver.exception.UnauthorizedException;

@RequiredArgsConstructor
@Component
public class JwtValidator {

	private final JwtGenerator jwtGenerator;

	public void validateAccessToken(String accessToken) {
		try {
			String role = parseToken(accessToken).get(JwtGenerator.CLAIM_MEMBER_DEFAULT_ROLE, String.class);
			if (role == null) {
				throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
			}
		} catch (ExpiredJwtException e) {
			throw new UnauthorizedException(EXPIRED_ACCESS_TOKEN);
		} catch (Exception e) {
			throw new UnauthorizedException(INVALID_ACCESS_TOKEN_VALUE);
		}
	}

	public void validateRefreshToken(final String refreshToken) {
		try {
			parseToken(getToken(refreshToken));
		} catch (ExpiredJwtException e) {
			throw new UnauthorizedException(EXPIRED_REFRESH_TOKEN);
		} catch (Exception e) {
			throw new UnauthorizedException(INVALID_REFRESH_TOKEN_VALUE);
		}
	}

	public void equalsRefreshToken(
			final String refreshToken,
			final String storedRefreshToken) {
		if (!getToken(refreshToken).equals(storedRefreshToken)) {
			throw new UnauthorizedException(MISMATCH_REFRESH_TOKEN);
		}
	}

	private String getToken(final String refreshToken) {
		if (refreshToken.startsWith(BEARER)) {
			return refreshToken.substring(BEARER.length());
		}
		throw new UnauthorizedException(MISSING_BEARER_PREFIX);
	}

	private Claims parseToken(final String token) {
		return jwtGenerator.getJwtParser().parseClaimsJws(token).getBody();
	}
}