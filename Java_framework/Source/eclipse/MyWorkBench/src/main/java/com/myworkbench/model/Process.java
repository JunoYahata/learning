package com.myworkbench.model;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "process")
public class Process {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

	@Column
	private Integer num;

	@Column
	private String processCd;

	@Column
	private String title;

	@Column
	private String emoji;

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

	@Transient
	@ManyToOne
	private Task task;

	@Transient
	@OneToMany
	private List<Record> records = new ArrayList<>();

}
