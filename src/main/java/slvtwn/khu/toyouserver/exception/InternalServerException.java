package slvtwn.khu.toyouserver.exception;

import slvtwn.khu.toyouserver.common.response.ResponseType;

public class InternalServerException extends ToyouException {
	public InternalServerException() {
		super(ResponseType.INTERNAL_SERVER_ERROR);
	}

	public InternalServerException(ResponseType responseType) {
		super(responseType);
	}
}
