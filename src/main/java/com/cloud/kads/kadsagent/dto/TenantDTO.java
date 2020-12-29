package com.cloud.kads.kadsagent.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

	@NotBlank(message = "email ID is mandatory")
	@NotNull
	private String email;

	@NotBlank(message = "first name is mandatory")
	private String firstName;

	@NotBlank(message = "last name is mandatory")
	private String lastName;

	private List<Long> contactNumber;

	private LandlordDTO landlord;

	private BankDTO bankDetails;
}
