package com.cloud.kads.kadsagent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cloud.kads.kadsagent.constants.CommonConstants;
import com.cloud.kads.kadsagent.domain.BankDetails;
import com.cloud.kads.kadsagent.domain.LandlordDetails;
import com.cloud.kads.kadsagent.domain.Tenant;
import com.cloud.kads.kadsagent.dto.BankDTO;
import com.cloud.kads.kadsagent.dto.GeneralErrorEnum;
import com.cloud.kads.kadsagent.dto.LandlordDTO;
import com.cloud.kads.kadsagent.dto.TenantDTO;
import com.cloud.kads.kadsagent.dto.TenantRequestDTO;
import com.cloud.kads.kadsagent.exceptions.AppRuntimeException;
import com.cloud.kads.kadsagent.repository.TenantRepository;

@Service
public class TenantServiceImpl implements TenantService {

	@Autowired
	TenantRepository tenantRepository;

	private static Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

	@Override
	public List<TenantDTO> getAllTenants() {
		List<TenantDTO> tenantDTOList = new ArrayList<>();
		Optional<List<Tenant>> tenants = tenantRepository.fetchAllTenants();
		if (tenants.isPresent()) {
			tenantDTOList = prepareTenantDTOList(tenants.get());
		}
		return tenantDTOList;
	}

	@Override
	public TenantDTO getTenant(Long tenantId) {

		logger.info("processing tenant = {}", tenantId);

		TenantDTO tenantDTO;
		Optional<Tenant> tenant = tenantRepository.findById(tenantId);
		if (!tenant.isPresent()) {

			logger.error(
					"tenant not found. Service failed with errorCode = {}, errorMessage = {} and, httpStatus = {} ",
					GeneralErrorEnum.ERR_MISSING_TENANT.getCode(),
					GeneralErrorEnum.ERR_MISSING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST.name());
			throw new AppRuntimeException(GeneralErrorEnum.ERR_MISSING_TENANT.getCode(),
					GeneralErrorEnum.ERR_MISSING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		tenantDTO = prepareTenantDTO(tenant.get());

		return tenantDTO;
	}

	@Override
	public TenantDTO createTenant(TenantRequestDTO tenantRequestDTO) {

		if (ObjectUtils.isEmpty(tenantRequestDTO.getRequestBody().getTenantDTO())
				|| null == tenantRequestDTO.getRequestBody().getTenantDTO().getEmail()) {
			throw new AppRuntimeException(GeneralErrorEnum.ERR_INVALID_REQUEST.getCode(),
					GeneralErrorEnum.ERR_INVALID_REQUEST.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}

		TenantDTO tenantDTO;
		Tenant tenant = new Tenant();

		TenantDTO requestDTO = tenantRequestDTO.getRequestBody().getTenantDTO();
		if (tenantExists(requestDTO)) {
			logger.error(
					"tenant already exist. Service failed with errorCode = {}, errorMessage = {} and, httpStatus = {} ",
					GeneralErrorEnum.ERR_ALREADY_EXISTING_TENANT.getCode(),
					GeneralErrorEnum.ERR_ALREADY_EXISTING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST.name());
			throw new AppRuntimeException(GeneralErrorEnum.ERR_ALREADY_EXISTING_TENANT.getCode(),
					GeneralErrorEnum.ERR_ALREADY_EXISTING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		saveTenant(requestDTO, tenant);
		tenantDTO = prepareTenantDTO(tenant);

		return tenantDTO;
	}

	@Override
	public TenantDTO updateTenant(TenantRequestDTO updateTenantDTO) {
		TenantDTO tenantDTO;
		Tenant tenant = fetchTenantById(updateTenantDTO.getRequestBody().getTenantDTO().getId());

		updateAndSaveTenant(tenant, updateTenantDTO.getRequestBody().getTenantDTO());
		tenantDTO = prepareTenantDTO(tenant);

		return tenantDTO;
	}

	@Override
	public TenantDTO deleteTenant(Long tenantId) {
		return deleteTenantAndSave(fetchTenantById(tenantId));
	}

	private Tenant saveTenant(TenantDTO tenantDTO, Tenant tenant) {

		BeanUtils.copyProperties(tenantDTO, tenant);

		tenant.setActiveInd(1);
		tenant.setFirstName(tenantDTO.getFirstName().toUpperCase(Locale.ENGLISH));
		tenant.setLastName(tenantDTO.getLastName().toUpperCase(Locale.ENGLISH));
		tenant.setContactNumber(getContactNumbersAsString(tenantDTO.getContactNumber()));
		tenant.setEmail(tenantDTO.getEmail());

		if (null != tenantDTO.getBankDetails()) {
			BankDetails bankDetails = new BankDetails();
			bankDetails.setAccountNumber(tenantDTO.getBankDetails().getAccountNumber());
			bankDetails.setBankName(tenantDTO.getBankDetails().getBankName());
			bankDetails.setNameOnAccount(tenantDTO.getBankDetails().getNameOnAccount());
			bankDetails.setActiveInd(1);
			tenant.setBankDetails(bankDetails);
		}

		if (null != tenantDTO.getLandlord()) {
			LandlordDetails landlordDetails = new LandlordDetails();
			landlordDetails.setDob(tenantDTO.getLandlord().getDob());
			landlordDetails.setEmailId(tenantDTO.getLandlord().getEmail());
			landlordDetails.setTitle(tenantDTO.getLandlord().getTitle().toUpperCase(Locale.ENGLISH));
			landlordDetails.setFirstName(tenantDTO.getLandlord().getFirstName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setMiddleName(tenantDTO.getLandlord().getMiddleName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setLastName(tenantDTO.getLandlord().getLastName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setGender(tenantDTO.getLandlord().getGender());
			landlordDetails.setContactNumber(getContactNumbersAsString(tenantDTO.getLandlord().getContactNumber()));
			landlordDetails.setActiveInd(1);
			tenant.setEmail(tenantDTO.getLandlord().getEmail());
			tenant.setLandlord(landlordDetails);
		}

		tenant.setStatus(CommonConstants.ADDED);
		tenantRepository.save(tenant);
		return tenant;

	}

	private void updateAndSaveTenant(Tenant tenant, TenantDTO tenantDTO) {
		BeanUtils.copyProperties(tenantDTO, tenant);
		tenant.setFirstName(tenantDTO.getFirstName().toUpperCase(Locale.ENGLISH));
		tenant.setLastName(tenantDTO.getLastName().toUpperCase(Locale.ENGLISH));
		tenant.setContactNumber(getContactNumbersAsString(tenantDTO.getContactNumber()));

		if (null != tenantDTO.getBankDetails()) {
			BankDetails bankDetails = tenant.getBankDetails();
			bankDetails.setAccountNumber(tenantDTO.getBankDetails().getAccountNumber());
			bankDetails.setBankName(tenantDTO.getBankDetails().getBankName());
			bankDetails.setNameOnAccount(tenantDTO.getBankDetails().getNameOnAccount());
			bankDetails.setActiveInd(1);
			tenant.setBankDetails(bankDetails);
		}

		if (null != tenantDTO.getLandlord()) {
			LandlordDetails landlordDetails = tenant.getLandlord();
			landlordDetails.setDob(tenantDTO.getLandlord().getDob());
			landlordDetails.setEmailId(tenantDTO.getLandlord().getEmail());
			landlordDetails.setTitle(tenantDTO.getLandlord().getTitle().toUpperCase(Locale.ENGLISH));
			landlordDetails.setFirstName(tenantDTO.getLandlord().getFirstName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setMiddleName(tenantDTO.getLandlord().getMiddleName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setLastName(tenantDTO.getLandlord().getLastName().toUpperCase(Locale.ENGLISH));
			landlordDetails.setGender(tenantDTO.getLandlord().getGender());
			landlordDetails.setContactNumber(getContactNumbersAsString(tenantDTO.getLandlord().getContactNumber()));
			landlordDetails.setActiveInd(1);
			tenant.setEmail(tenantDTO.getLandlord().getEmail());
			tenant.setLandlord(landlordDetails);
		}
		tenant.setStatus(CommonConstants.UPDATED);
		tenantRepository.save(tenant);

	}

	private Tenant fetchTenantById(Long tenantId) {

		Optional<Tenant> tenantEntity = tenantRepository.findById(tenantId);
		if (!tenantEntity.isPresent()) {
			logger.error(
					"tenant not found. Service failed with errorCode = {}, errorMessage = {} and, httpStatus = {} ",
					GeneralErrorEnum.ERR_MISSING_TENANT.getCode(),
					GeneralErrorEnum.ERR_MISSING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST.name());
			throw new AppRuntimeException(GeneralErrorEnum.ERR_MISSING_TENANT.getCode(),
					GeneralErrorEnum.ERR_MISSING_TENANT.getErrorMessage(), HttpStatus.BAD_REQUEST);
		}
		return tenantEntity.get();
	}

	private TenantDTO deleteTenantAndSave(Tenant tenant) {
		TenantDTO tenantDTO = new TenantDTO();
		if (tenant != null) {
			tenant.setActiveInd(0);
			tenant.setStatus(CommonConstants.DELETED);
			LandlordDetails landlordDetails = tenant.getLandlord();
			landlordDetails.setActiveInd(0);
			tenant.setLandlord(landlordDetails);
			BankDetails bankDetails = tenant.getBankDetails();
			bankDetails.setActiveInd(0);
			tenant.setBankDetails(bankDetails);
			tenantRepository.save(tenant);
			tenantDTO = prepareTenantDTO(tenant);
		}

		return tenantDTO;
	}

	private String getContactNumbersAsString(List<Long> contactNumbers) {
		if (!ObjectUtils.isEmpty(contactNumbers)) {
			return contactNumbers.stream().map(number -> number.toString()).collect(Collectors.joining(","));
		}
		logger.debug("contact number not found");
		return StringUtils.EMPTY;
	}

	private boolean tenantExists(TenantDTO requestDTO) {
		Optional<Tenant> tenantEntity = tenantRepository.findByEmailId(requestDTO.getEmail());
		if (tenantEntity.isPresent()) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private List<TenantDTO> prepareTenantDTOList(List<Tenant> liTenants) {
		List<TenantDTO> tenants = new ArrayList<>();
		liTenants.stream().forEach(tenant -> tenants.add(prepareTenantDTO(tenant)));
		return tenants;
	}

	private TenantDTO prepareTenantDTO(Tenant tenant) {
		TenantDTO tenantDTO = new TenantDTO();
		BankDTO bankDTO = new BankDTO();
		LandlordDTO landlordDTO = new LandlordDTO();
		BeanUtils.copyProperties(tenant, tenantDTO);
		if (null != tenant.getBankDetails()) {
			BeanUtils.copyProperties(tenant.getBankDetails(), bankDTO);
			tenantDTO.setBankDetails(bankDTO);
		}

		if (null != tenant.getLandlord()) {
			BeanUtils.copyProperties(tenant.getLandlord(), landlordDTO);
			tenantDTO.setLandlord(landlordDTO);
		}
		return tenantDTO;
	}

}
