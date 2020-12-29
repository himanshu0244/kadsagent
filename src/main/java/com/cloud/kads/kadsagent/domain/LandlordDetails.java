package com.cloud.kads.kadsagent.domain;

import static com.cloud.kads.kadsagent.constants.CommonConstants.ACTIVE_IND;
import static com.cloud.kads.kadsagent.constants.CommonConstants.CONTACT_NUMBER;
import static com.cloud.kads.kadsagent.constants.CommonConstants.DOB;
import static com.cloud.kads.kadsagent.constants.CommonConstants.EMAIL;
import static com.cloud.kads.kadsagent.constants.CommonConstants.FIRST_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.GENDER;
import static com.cloud.kads.kadsagent.constants.CommonConstants.ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.IMAGE;
import static com.cloud.kads.kadsagent.constants.CommonConstants.LANDLORD_DETAILS;
import static com.cloud.kads.kadsagent.constants.CommonConstants.LANDLORD_DETAILS_SEQ;
import static com.cloud.kads.kadsagent.constants.CommonConstants.LAST_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.MIDDLE_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.TITLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = LANDLORD_DETAILS, uniqueConstraints = { @UniqueConstraint(columnNames = ID) })
public class LandlordDetails extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = LANDLORD_DETAILS_SEQ, strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = LANDLORD_DETAILS_SEQ, sequenceName = LANDLORD_DETAILS_SEQ, allocationSize = 1)
	@Column(name = ID, updatable = false, nullable = false)
	private Long id;

	@Column(name = ACTIVE_IND)
	private int activeInd;

	@Lob
	@Column(name = IMAGE, nullable = true)
	private byte[] image;

	@Column(name = TITLE)
	private String title;

	@Column(name = FIRST_NAME)
	private String firstName;

	@Column(name = MIDDLE_NAME, nullable = true)
	private String middleName;

	@Column(name = LAST_NAME)
	private String lastName;

	@Column(name = EMAIL, nullable = false)
	private String emailId;

	@Column(name = DOB)
	private String dob;

	@Column(name = GENDER)
	private String gender;

	@Column(name = CONTACT_NUMBER, nullable = true)
	private String contactNumber;

}
