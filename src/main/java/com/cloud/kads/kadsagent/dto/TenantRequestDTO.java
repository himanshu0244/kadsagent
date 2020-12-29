package com.cloud.kads.kadsagent.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantRequestDTO {

	private RequestHeaderDTO requestHeader;

	@NotNull(message = "request Body cannot be null")
	private TenantRequestBodyDTO requestBody;
}
