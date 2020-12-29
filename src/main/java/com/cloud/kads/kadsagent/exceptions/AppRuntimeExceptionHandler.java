package com.cloud.kads.kadsagent.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cloud.kads.kadsagent.dto.ResponseHeaderDTO;
import com.cloud.kads.kadsagent.utils.ApplicationUtils;

@ControllerAdvice
public class AppRuntimeExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String MESSAGE = "message";

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> handleDataAccessException(RuntimeException ex, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());
		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { AppRuntimeException.class })
	protected ResponseEntity<Object> handleAppRuntimeException(RuntimeException ex, WebRequest request) {
		ResponseHeaderDTO prepareErroResponse;
		prepareErroResponse = ApplicationUtils.prepareErroResponse(ex);
		return handleExceptionInternal(ex, prepareErroResponse, new HttpHeaders(),
				((AppRuntimeException) ex).getHttpStatus(), request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		errors.put(MESSAGE, ex.getMessage());

		return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}
