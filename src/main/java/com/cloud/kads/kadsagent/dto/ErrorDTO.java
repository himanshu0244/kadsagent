package com.cloud.kads.kadsagent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * ErrorDTO
 * 
 * @author hgoel@244
 *
 */
@JsonInclude(Include.NON_NULL)
public class ErrorDTO {
	private int errorCode;
	private String errorMessages;
	private String errorType;

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}

	@Override
	public String toString() {
		return "ErrorDTO [errorCode=" + errorCode + ", errorMessages=" + errorMessages + ", errorType=" + errorType
				+ "]";
	}

}
