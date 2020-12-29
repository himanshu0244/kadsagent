package com.cloud.kads.kadsagent.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.cloud.kads.kadsagent.domain.Tenant;

public interface TenantRepository extends CrudRepository<Tenant, Long> {

	@Query("Select tenant from Tenant tenant where tenant.activeInd=1 order by createDate desc")
	Optional<List<Tenant>> fetchAllTenants();

	@Query("Select tenant from Tenant tenant where tenant.email= :email and tenant.activeInd=1")
	Optional<Tenant> findByEmailId(String email);
}
