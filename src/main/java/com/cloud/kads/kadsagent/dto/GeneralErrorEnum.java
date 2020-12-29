package com.cloud.kads.kadsagent.dto;

public enum GeneralErrorEnum {

	SUCCESS(100, "SUCCESS", "Success"), FAILED(101, "FAILED", "Failed"),
	ERR_INVALID_REQUEST(102, "ERR_INVALID_REQUEST", "Invalid Request"),
	ERR_MISSING_TENANT(103, "ERR_MISSING_TENANT", "tenant not found"),
	ERR_ALREADY_EXISTING_TENANT(104, "ERR_ALREADY_EXISTING_TENANT", "tenant already exists"),

	UNKNOWN(0, "UNKNOWN", "Unknown");

	private int code;
	private String errorCode;
	private String errorMessage;

	GeneralErrorEnum(int code, String errorCode, String errorMessage) {
		this.code = code;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getCode() {
		return code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
