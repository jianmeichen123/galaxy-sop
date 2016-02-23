package com.galaxyinternet.exception;

import com.galaxyinternet.framework.core.exception.BusinessException;

public class PlatformException extends BusinessException {

	private static final long serialVersionUID = 1L;
	
	public PlatformException(int code, String message, Throwable throwable) {
		super(code, message, throwable);
	}

	public PlatformException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public PlatformException(int code, String message) {
		super(code, message);
	}

	public PlatformException(String message) {
		super(message);
	}

	public PlatformException(Throwable arg0) {
		super(arg0);
	}
	
}
