package com.myworkbench.model;

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
@Table(name="process")
public class Process {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column
	private Integer num;
	
	@Column
	private String processCd;
	
	@Column
	private String title;
	
	@Column
	private String link;
	
	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;
	
	@Column
	@LastModifiedDate
	private Timestamp updateTime;
	
	@Column
	private UUID paUid;
	

	@Column
	private UUID uid;
	
	
	//private Task task;
	
	//private List<Record> reords = new ArrayList<>();
	
	
}
