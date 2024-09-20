package com.myworkbench.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

	@Column
	private String tagCd;

	@Column
	private String classCd;

	@Column
	private String statusCd;

	@Column
	private String title;

	@Column
	private String titleShort;

	@Column
	private String details;

	@Column
	private Date plannedStartDate;

	@Column
	private Date plannedEndDate;

	@Column
	private Date startDate;

	@Column
	private Date endDate;

	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;

	@Column
	@LastModifiedDate
	private Timestamp updateTime;
	
	@Transient
	private String tagCdName;

	@Transient
	private String classCdName;

	@Transient
	private String statusCdName;

	@Transient
	@OneToMany
	private List<Process> processes;

}
