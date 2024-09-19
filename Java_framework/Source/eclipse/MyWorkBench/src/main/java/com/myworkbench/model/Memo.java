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
@Table(name = "memo")
public class Memo {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

	@Column
	private String tagCd;

	@Column
	private String title;

	@Column
	private String details;

	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;

	@Column
	@LastModifiedDate
	private Timestamp updateTime;
}
