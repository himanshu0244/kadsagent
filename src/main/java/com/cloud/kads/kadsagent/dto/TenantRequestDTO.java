package com.cloud.kads.kadsagent.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantRequestDTO {

	private RequestHeaderDTO requestHeader;

	private TenantRequestBodyDTO requestBody;
}
