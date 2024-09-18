package com.myworkbench.model;

import java.sql.Date;
import java.sql.Timestamp;
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
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="task")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
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
	
	@Column
	private UUID uid;
	
	//private List<Process> processes;
	
	
}
