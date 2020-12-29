package com.cloud.kads.kadsagent.service;

import java.util.List;

import com.cloud.kads.kadsagent.dto.TenantDTO;
import com.cloud.kads.kadsagent.dto.TenantRequestDTO;
import com.cloud.kads.kadsagent.exceptions.AppRuntimeException;

public interface TenantService {

	List<TenantDTO> getAllTenants();

	TenantDTO getTenant(Long tenantId) throws AppRuntimeException;

	TenantDTO createTenant(TenantRequestDTO tenantRequestDTO)throws AppRuntimeException;

	TenantDTO updateTenant(TenantRequestDTO updateTenantDTO)throws AppRuntimeException;

	TenantDTO deleteTenant(Long tenantId)throws AppRuntimeException;

}
