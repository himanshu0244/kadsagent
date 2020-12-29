package com.cloud.kads.kadsagent.domain;

import static com.cloud.kads.kadsagent.constants.CommonConstants.ACCOUNT_NUMBER;
import static com.cloud.kads.kadsagent.constants.CommonConstants.ACTIVE_IND;
import static com.cloud.kads.kadsagent.constants.CommonConstants.BANK_DETAILS;
import static com.cloud.kads.kadsagent.constants.CommonConstants.BANK_DETAILS_SEQ;
import static com.cloud.kads.kadsagent.constants.CommonConstants.BANK_NAME;
import static com.cloud.kads.kadsagent.constants.CommonConstants.ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.NAME_ON_ACCOUNT;
import static com.cloud.kads.kadsagent.constants.CommonConstants.SHOW_ACCOUNT;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = BANK_DETAILS, uniqueConstraints = { @UniqueConstraint(columnNames = ID),
		@UniqueConstraint(columnNames = ACCOUNT_NUMBER) })
public class BankDetails extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = BANK_DETAILS_SEQ, strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = BANK_DETAILS_SEQ, sequenceName = BANK_DETAILS_SEQ, allocationSize = 1)
	@Column(name = ID, updatable = false, nullable = false)
	private Long id;

	@Column(name = ACTIVE_IND)
	private int activeInd;

	@Column(name = BANK_NAME)
	private String bankName;

	@Column(name = ACCOUNT_NUMBER, nullable = false)
	private Long accountNumber;

	@Column(name = NAME_ON_ACCOUNT)
	private String nameOnAccount;

	@Column(name = SHOW_ACCOUNT)
	private Boolean showAccount;
}
