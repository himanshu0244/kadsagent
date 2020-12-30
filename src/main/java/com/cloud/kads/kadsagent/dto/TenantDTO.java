package com.cloud.kads.kadsagent.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class TenantDTO {

	private Long id;

	private byte[] image;

	private String email;

	private String firstName;

	private String lastName;

	private List<Long> contactNumber;

	private LandlordDTO landlord;

	private BankDTO bankDetails;
}
