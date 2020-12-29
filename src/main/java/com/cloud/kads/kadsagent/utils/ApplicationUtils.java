package com.cloud.kads.kadsagent.utils;

import java.util.UUID;

import com.cloud.kads.kadsagent.dto.ErrorDTO;
import com.cloud.kads.kadsagent.dto.GeneralErrorEnum;
import com.cloud.kads.kadsagent.dto.ResponseHeaderDTO;
import com.cloud.kads.kadsagent.exceptions.AppRuntimeException;

/**
 * ApplicationUtils is the utility class that provide utilities at the
 * application level.
 * 
 * @author hgoel_244
 * 
 */
public class ApplicationUtils {

	private ApplicationUtils() {

	}

	/**
	 * This method prepare the response header for success scenario.
	 * 
	 * @param startTime{@link -> String}
	 * @return responseHeaderDTO {@link -> ResponseHeaderDTO}
	 * @since 1.0
	 * @version 1.0
	 */
	public static ResponseHeaderDTO prepareResponseHeader(long startTime) {
		long endTime = System.currentTimeMillis();
		return prepareResponseHeaderObject(GeneralErrorEnum.SUCCESS, String.valueOf(endTime - startTime));
	}

	/**
	 * This method prepare the response header object for success scenario.
	 * 
	 * @param responseStatus {@link -> GeneralErrorEnum}
	 * @param responseTime   {@link -> String}
	 * @return responseHeaderDTO {@link -> ResponseHeaderDTO}
	 * @since 1.0
	 * @version 1.0
	 */
	private static ResponseHeaderDTO prepareResponseHeaderObject(GeneralErrorEnum responseStatus, String responseTime) {
		ResponseHeaderDTO responseHeaderDTO = new ResponseHeaderDTO();
		responseHeaderDTO.setTransactionId(UUID.randomUUID().toString());
		responseHeaderDTO.setResponseCode(responseStatus.getCode());
		if (null != responseTime) {
			responseHeaderDTO.setResponseTime(responseTime);
		}
		return responseHeaderDTO;
	}

	/**
	 * This method prepare the response header for error scenario.
	 * 
	 * @param ex {@link -> RuntimeException}
	 * @return responseHeaderDTO {@link -> ResponseHeaderDTO}
	 * @since 1.0
	 * @version 1.0
	 */
	public static ResponseHeaderDTO prepareErroResponse(RuntimeException ex) {

		ResponseHeaderDTO responseHeaderDTO = prepareErrorResponseHeaderObject();
		ErrorDTO errorDTO = new ErrorDTO();
		if (ex instanceof AppRuntimeException) {
			errorDTO.setErrorCode(((AppRuntimeException) ex).getCode());
			errorDTO.setErrorMessages(((AppRuntimeException) ex).getErrorMessage());
			errorDTO.setErrorType(((AppRuntimeException) ex).getHttpStatus().name());
			responseHeaderDTO.setError(errorDTO);
		}
		return responseHeaderDTO;

	}

	/**
	 * This method prepare the response header object for error scenario
	 * 
	 * @param ex {@link -> RuntimeException}
	 * @return responseHeaderDTO {@link -> ResponseHeaderDTO}
	 * @since 1.0
	 * @version 1.0
	 */
	private static ResponseHeaderDTO prepareErrorResponseHeaderObject() {
		ResponseHeaderDTO responseHeaderDTO = new ResponseHeaderDTO();
		responseHeaderDTO.setTransactionId(UUID.randomUUID().toString());
		return responseHeaderDTO;
	}

}
