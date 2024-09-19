package com.myworkbench.model;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="record")
public class Record {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column
	private UUID uid;
	
	@Column
	private Integer num;
	
	@Column
	private Timestamp startTime;
	
	@Column
	private Timestamp stopTime;
	
	@Column
	private String memo;
	
	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;
	
	@Column
	@LastModifiedDate
	private Timestamp updateTime;
	
	@Column
	private UUID paUid;
	
	@ManyToOne
	private Process process;
	
	
}
