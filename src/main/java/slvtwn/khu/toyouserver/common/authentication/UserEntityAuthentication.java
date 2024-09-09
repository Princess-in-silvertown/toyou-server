package slvtwn.khu.toyouserver.common.authentication;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserEntityAuthentication extends UsernamePasswordAuthenticationToken {

	private UserEntityAuthentication(
			Object principal,
			Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public static UserEntityAuthentication createMemberAuthentication(Long userId) {
		return new UserEntityAuthentication(userId, null, null);
	}
}
