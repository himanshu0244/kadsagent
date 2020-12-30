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
public class LandlordDTO {

	private Long id;

	private byte[] image;

	private String email;

	private String title;

	private String firstName;

	private String middleName;

	private String lastName;

	private String dob;

	private String gender;

	private List<Long> contactNumber;
}
