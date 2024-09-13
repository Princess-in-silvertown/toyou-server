package slvtwn.khu.toyouserver.exception;

import slvtwn.khu.toyouserver.common.response.ResponseType;

public class UnauthorizedException extends ToyouException {
	public UnauthorizedException() {
		super(ResponseType.UNAUTHORIZED_USER_ACCESS);
	}

	public UnauthorizedException(ResponseType responseType) {
		super(responseType);
	}
}
