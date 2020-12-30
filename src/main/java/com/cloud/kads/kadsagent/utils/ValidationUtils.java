package com.cloud.kads.kadsagent.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import com.cloud.kads.kadsagent.dto.TenantDTO;
import com.cloud.kads.kadsagent.dto.TenantRequestDTO;
import com.cloud.kads.kadsagent.exceptions.AppRuntimeException;
import com.google.common.base.Preconditions;

public class ValidationUtils {

	private ValidationUtils() {
	}

	public static void validate(TenantRequestDTO request) {
		try {
			Preconditions.checkNotNull(request, "request object can not be null");
			Preconditions.checkNotNull(request.getRequestBody(), "request body can not be null");
			Preconditions.checkNotNull(request.getRequestBody().getTenantDTO(),
					"tenant request object can not be null");
			validateRequestBody(request.getRequestBody().getTenantDTO());
		} catch (Exception ex) {
			throw new AppRuntimeException(400, ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private static void validateRequestBody(TenantDTO tenantDTO) {
		Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getFirstName()), "tenant's first name is empty");
		Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getLastName()), "tenant's last name is empty");
		Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getEmail()), "tenant's email ID is empty");

		if (null != tenantDTO.getLandlord()) {
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getLandlord().getEmail()),
					"landlord's email ID is empty");
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getLandlord().getTitle()),
					"landlord's Title ID is empty");
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getLandlord().getFirstName()),
					"landlord's first name is empty");
			Preconditions.checkNotNull(tenantDTO.getLandlord().getMiddleName(), "landlord's middle name is null");
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getLandlord().getLastName()),
					"landlord's last name is empty");
		}

		if (null != tenantDTO.getBankDetails()) {
			Preconditions.checkArgument(ObjectUtils.isNotEmpty(tenantDTO.getBankDetails().getAccountNumber()),
					"bank account number is empty");
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getBankDetails().getBankName()),
					"bank name is empty");
			Preconditions.checkArgument(StringUtils.hasText(tenantDTO.getBankDetails().getNameOnAccount()),
					" name on account is empty");
		}

	}
}
