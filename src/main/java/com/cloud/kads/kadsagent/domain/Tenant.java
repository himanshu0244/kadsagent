package com.cloud.kads.kadsagent.domain;

import static com.cloud.kads.kadsagent.constants.CommonConstants.ACTIVE_IND;
import static com.cloud.kads.kadsagent.constants.CommonConstants.ID;
import static com.cloud.kads.kadsagent.constants.CommonConstants.IMAGE;
import static com.cloud.kads.kadsagent.constants.CommonConstants.TENANT;
import static com.cloud.kads.kadsagent.constants.CommonConstants.TENANT_SEQ;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = TENANT)
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
	@Column(name = IMAGE)
	private byte[] image;

}
