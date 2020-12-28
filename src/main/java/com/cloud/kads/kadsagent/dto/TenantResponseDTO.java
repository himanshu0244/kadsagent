package com.cloud.kads.kadsagent.dto;

import lombok.Data;

@Data
public class TenantResponseDTO {
	private ResponseHeaderDTO responseHeader;

	private TenantResponseResponseBody responseBody;
}
