package com.cloud.kads.kadsagent.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.kads.kadsagent.constants.CommonConstants;
import com.cloud.kads.kadsagent.dto.TenantDTO;
import com.cloud.kads.kadsagent.dto.TenantRequestDTO;
import com.cloud.kads.kadsagent.dto.TenantResponseDTO;
import com.cloud.kads.kadsagent.dto.TenantResponseResponseBody;
import com.cloud.kads.kadsagent.service.TenantServiceImpl;
import com.cloud.kads.kadsagent.utils.ApplicationUtils;

/**
 * application Controller
 * 
 * @author hgoel@244
 *
 */
@Validated
@RestController
@RequestMapping(value = CommonConstants.AGENT_BASE_URL + CommonConstants.API_VERSION)
public class KadsAgentController {

	@Autowired
	private TenantServiceImpl tenantService;

	@GetMapping
	public TenantResponseDTO getAllTenants() {
		long startTime = System.currentTimeMillis();
		List<TenantDTO> tenants = tenantService.getAllTenants();
		return prepareTenantResponse(tenants, startTime);

	}

	@GetMapping("/{tenantId}")
	public TenantResponseDTO getTenant(@PathVariable @NotNull(message = "tenant Id cannot be null ") Long tenantId) {
		long startTime = System.currentTimeMillis();
		TenantDTO tenant = tenantService.getTenant(tenantId);
		return prepareTenantResponse(Arrays.asList(tenant), startTime);
	}

	@PostMapping
	public TenantResponseDTO createTenant(@Valid @RequestBody TenantRequestDTO tenantRequestDTO) {
		long startTime = System.currentTimeMillis();

		TenantDTO tenant = tenantService.createTenant(tenantRequestDTO);
		return prepareTenantResponse(Arrays.asList(tenant), startTime);
	}

	@PutMapping
	public TenantResponseDTO updateTenant(@Valid @RequestBody TenantRequestDTO tenantRequestDTO) {
		long startTime = System.currentTimeMillis();
		TenantDTO tenant = tenantService.updateTenant(tenantRequestDTO);
		return prepareTenantResponse(Arrays.asList(tenant), startTime);
	}

	@DeleteMapping("/{tenantId}")
	public TenantResponseDTO deleteTenant(@PathVariable @NotNull(message = "tenant Id cannot be null ") Long tenantId) {
		long startTime = System.currentTimeMillis();
		TenantDTO tenant = tenantService.deleteTenant(tenantId);
		return prepareTenantResponse(Arrays.asList(tenant), startTime);
	}

	private TenantResponseDTO prepareTenantResponse(List<TenantDTO> tenants, long startTime) {
		TenantResponseDTO tenantResponse = new TenantResponseDTO();
		tenantResponse.setResponseBody(new TenantResponseResponseBody());
		tenantResponse.getResponseBody().setTenants(tenants);
		tenantResponse.setResponseHeader(ApplicationUtils.prepareResponseHeader(startTime));
		return tenantResponse;
	}

}
