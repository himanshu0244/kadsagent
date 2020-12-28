package com.cloud.kads.kadsagent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.kads.kadsagent.constants.CommonConstants;
import com.cloud.kads.kadsagent.domain.Tenant;
import com.cloud.kads.kadsagent.dto.TenantDTO;
import com.cloud.kads.kadsagent.dto.TenantRequestDTO;
import com.cloud.kads.kadsagent.dto.TenantResponseDTO;
import com.cloud.kads.kadsagent.dto.TenantResponseResponseBody;
import com.cloud.kads.kadsagent.repository.TenantRepository;

@Service
public class TenantService {
	
	@Autowired
	TenantRepository tenantRepository;

	public List<TenantDTO> getAllTenants() {
		List<TenantDTO> tenantDTOList = new ArrayList<>();
		Optional<List<Tenant>> tenants = tenantRepository.fetchAllTenants();
		if (tenants.isPresent()) {
			tenantDTOList = prepareTenantDTOList(tenants.get());
		}
		return tenantDTOList;
	}

	public TenantDTO getTenant(Long tenantId) {
		TenantDTO tenantDTO = new TenantDTO();
		Optional<Tenant> tenant = tenantRepository.findById(tenantId);
		if (tenant.isPresent()) {
			tenantDTO = prepareTenantDTO(tenant.get());
		}
		return tenantDTO;
	}

	public TenantDTO createTenant(TenantRequestDTO tenantRequestDTO) {
		TenantDTO tenantDTO = new TenantDTO();
		Tenant tenant = new Tenant();
		StringBuilder messageBuilder = new StringBuilder();

		TenantDTO requestDTO = tenantRequestDTO.getRequestBody().getTenantDTO();
		if (!tenantExists(requestDTO)) {
			saveTenant(tenantDTO, tenant);
			tenantDTO = prepareTenantDTO(tenant);
		} else {
			messageBuilder.append("Visitor Already Exists");
		}
		return tenantDTO;
	}

	private Tenant saveTenant(TenantDTO tenantDTO, Tenant tenant) {
		BeanUtils.copyProperties(tenantDTO, tenant);
//		tenant.setActiveInd(1);
//		tenant.setFirstName(tenantDTO.getFirstName().toUpperCase(Locale.ENGLISH));
//		tenant.setLastName(tenantDTO.getLastName().toUpperCase(Locale.ENGLISH));
//		tenant.setStatus(CommonConstants.ADDED);
		tenantRepository.save(tenant);
		return tenant;

	}

	private boolean tenantExists(TenantDTO requestDTO) {
		Optional<Tenant> tenantEntity = tenantRepository.findByEmployeeId(requestDTO.getVisitorEmpId());
		if (tenantEntity.isPresent()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public TenantDTO updateTenant(TenantRequestDTO updateTenantDTO) {
		TenantDTO tenantDTO = new TenantDTO();
		Tenant tenant = fetchTenantById(updateTenantDTO.getRequestBody().getTenantDTO().getId());
		StringBuilder messageBuilder = new StringBuilder();
		List<TenantDTO> visitorDTOList = null;
		if (tenant != null) {
			updateAndSaveTenant(tenant, updateTenantDTO.getRequestBody().getTenantDTO());
			tenantDTO = prepareTenantDTO(tenant);
		} else {
			messageBuilder.append("visitor cannot be updated");
		}
		return tenantDTO;
	}

	private void updateAndSaveTenant(Tenant tenant, TenantDTO tenantDTO) {
		BeanUtils.copyProperties(tenantDTO, tenant);
		tenant.setFirstName(tenantDTO.getFirstName().toUpperCase(Locale.ENGLISH));
		tenant.setLastName(tenantDTO.getLastName().toUpperCase(Locale.ENGLISH));
		tenantRepository.save(tenant);

	}

	public TenantDTO deleteTenant(Long tenantId) {
		return deleteTenantAndSave(fetchTenantById(tenantId));
	}

	private TenantDTO deleteTenantAndSave(Tenant tenant) {
		TenantDTO tenantDTO = new TenantDTO();
		if (tenant != null) {
			tenant.setActiveInd(0);
			tenant.setStatus(CommonConstants.DELETED);
			tenantRepository.save(tenant);
			tenantDTO = prepareTenantDTO(tenant);
		}

		return tenantDTO;
	}

	private Tenant fetchTenantById(Long tenantId) {

		Optional<Tenant> tenantEntity = tenantRepository.findById(tenantId);
		if (tenantEntity.isPresent()) {
			return tenantEntity.get();
		}
		return null;
	}

	private TenantResponseDTO prepareTenantResponse(List<TenantDTO> liTenants, String message) {
		TenantResponseDTO tenantResponseDTO = new TenantResponseDTO();
		tenantResponseDTO.setResponseBody(new TenantResponseResponseBody());
		if (!StringUtils.isEmpty(message)) {
			tenantResponseDTO.getResponseBody().setMessage(message);
		} else {
			tenantResponseDTO.getResponseBody().setVisitors(liTenants);
		}
		return tenantResponseDTO;
	}

	private List<TenantDTO> prepareTenantDTOList(List<Tenant> liTenants) {
		List<TenantDTO> tenants = new ArrayList<>();
		liTenants.stream().forEach(tenant -> {
			tenants.add(prepareTenantDTO(tenant));
		});
		return tenants;
	}

	private TenantDTO prepareTenantDTO(Tenant tenant) {
		TenantDTO tenantDTO = new TenantDTO();
		BeanUtils.copyProperties(tenant, tenantDTO);
		return tenantDTO;
	}

}
