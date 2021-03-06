package com.cloud.kads.kadsagent.domain;

import static com.cloud.kads.kadsagent.constants.CommonConstants.ACTIVE_IND;
import static com.cloud.kads.kadsagent.constants.CommonConstants.BANK_ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.CONTACT_NUMBER;
import static com.cloud.kads.kadsagent.constants.CommonConstants.EMAIL;
import static com.cloud.kads.kadsagent.constants.CommonConstants.FIRST_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.IMAGE;
import static com.cloud.kads.kadsagent.constants.CommonConstants.LANDLORD_ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.LAST_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.STATUS;
import static com.cloud.kads.kadsagent.constants.CommonConstants.TENANT;
import static com.cloud.kads.kadsagent.constants.CommonConstants.TENANT_SEQ;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
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
@Table(name = TENANT, uniqueConstraints = { @UniqueConstraint(columnNames = ID),
		@UniqueConstraint(columnNames = EMAIL) })
public class Tenant extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = TENANT_SEQ, strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = TENANT_SEQ, sequenceName = TENANT_SEQ, allocationSize = 1)
	@Column(name = ID, updatable = false, nullable = false)
	private Long id;

	@Column(name = ACTIVE_IND)
	private int activeInd;

	@Lob
	@Column(name = IMAGE, nullable = true)
	private byte[] image;

	@Column(name = EMAIL)
	private String email;

	@Column(name = FIRST_NAME)
	private String firstName;

	@Column(name = LAST_NAME)
	private String lastName;

	@Column(name = CONTACT_NUMBER)
	private String contactNumber;

	@Column(name = STATUS)
	private String status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = LANDLORD_ID, referencedColumnName = ID)
	private LandlordDetails landlord;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = BANK_ID, referencedColumnName = ID)
	private BankDetails bankDetails;

}
