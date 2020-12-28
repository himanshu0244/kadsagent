package com.cloud.kads.kadsagent.exceptions;

import org.springframework.http.HttpStatus;

/**
 * {@code AppRuntimeException} is the class of those exceptions that can be
 * thrown during the normal operation of the application, so that the exceptions
 * are not thrown at the Java Virtual Machine
 * 
 * 
 * @author hgoel_244
 * @version 1.0
 * 
 */

public class AppRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * error code
	 */
	private final int code;

	/**
	 * error message
	 */
	private final String errorMessage;

	/**
	 * HTTP status
	 */
	private final HttpStatus httpStatus;

	/**
	 * Constructs a new app runtime exception with specified code, message and
	 * httpStatus
	 * 
	 * @param code       code is saved for retrieval by the {@link #getCode()}
	 *                   method.
	 * @param message    code is saved for retrieval by the
	 *                   {@link #getErrorMessage()} method.
	 * @param httpStatus code is saved for retrieval by the {@link #getHttpStatus()}
	 *                   method.
	 * @since 1.7
	 */
	public AppRuntimeException(int code, String message, HttpStatus httpStatus) {
		super();
		this.code = code;
		this.errorMessage = message;
		this.httpStatus = httpStatus;
	}

	public int getCode() {
		return code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
