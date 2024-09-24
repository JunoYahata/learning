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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "record")
public class Record {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

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

	@Transient
	@ManyToOne
	private Process process;

	@Transient
	private String startTimeStr;
	@Transient
	private String stopTimeStr;
	@Transient
	private UUID gpaUid;

	public void setTimes() {
		this.setStartTime(Timestamp.valueOf(startTimeStr.concat(":00")));
		this.setStopTime(Timestamp.valueOf(stopTimeStr.concat(":00")));
	}

	public void setTimesStr() {
		this.setStartTimeStr(startTime.toString().substring(0, 16));
		this.setStopTimeStr(stopTime.toString().substring(0, 16));

	}

}
