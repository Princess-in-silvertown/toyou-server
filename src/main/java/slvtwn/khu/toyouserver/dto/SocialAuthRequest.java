package slvtwn.khu.toyouserver.dto;

import slvtwn.khu.toyouserver.domain.SocialAuthProvider;

public record SocialAuthRequest(
		SocialAuthProvider provider,
		String authorizationCode
) {
}
