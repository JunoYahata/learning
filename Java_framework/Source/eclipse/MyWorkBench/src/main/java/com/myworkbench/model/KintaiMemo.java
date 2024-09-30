package com.myworkbench.model;

import java.sql.Timestamp;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "kintai_memo")
public class KintaiMemo {

	@Id
	@Column
	private String day;

	@Column
	private Timestamp attendTime;

	@Column
	private Timestamp leaveTime;

	@Transient
	private int workTime;

	public void calcTimes() {
		if (attendTime != null && leaveTime != null) {

			workTime = (int) ((leaveTime.getTime() - attendTime.getTime()) / 1000 / 60 / 15) * 15 - 60;
		} else {
			workTime = 0;
		}
	}

}
