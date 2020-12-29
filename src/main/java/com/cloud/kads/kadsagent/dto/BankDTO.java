package com.cloud.kads.kadsagent.dto;

import javax.validation.constraints.NotBlank;

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
public class BankDTO {
	
	@NotBlank(message = "bankName is mandatory")
	private String bankName;
	
	@NotBlank(message = "accountNumber is mandatory")
	private Long accountNumber;
	
	@NotBlank(message = "nameOnAccount is mandatory")
	private String nameOnAccount;
}
