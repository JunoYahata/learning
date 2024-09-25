package com.myworkbench.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
import jakarta.persistence.Transient;
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

	@Column
	private String time = "0";

	@Column(updatable = false)
	@CreatedDate
	private Timestamp createTime;

	@Column
	@LastModifiedDate
	private Timestamp updateTime;

	@Transient
	private long epochTime;

	public void calcEpochTime() {

		if (this.time == "0") {
			this.setEpochTime(Long.valueOf(this.time));
		} else {
			String time = this.time;
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expiration = null;
			int day = 0;
			int hour = 0;
			int minute = 0;
			if (time.split(",").length == 2) {
				day = Integer.parseInt(time.split(",")[0]);
				time = time.split(",")[1];
			}
			if (time.split(":").length == 2) {
				hour = Integer.parseInt(time.split(":")[0]);
				minute = Integer.parseInt(time.split(":")[1]);
				expiration = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), hour, minute)
						.plusDays(day);
			} else if (time.split(":").length == 1) {
				minute = Integer.parseInt(time);
				expiration = now.plusDays(day).plusMinutes(minute);
			}
			if (expiration != null) {
				this.setEpochTime(expiration.toEpochSecond(ZoneOffset.ofHours(9)));
			} else {
				this.setEpochTime(Long.valueOf(this.time));
			}

		}

	}

}
