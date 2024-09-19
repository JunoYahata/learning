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
@Table(name = "slack")
public class Slack {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

	@Column
	private String slackCd;

	@Column
	private String title;

	@Column
	private String message;

	@Column
	private String statusEmoji;

	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;

	@Column
	@LastModifiedDate
	private Timestamp updateTime;

}
