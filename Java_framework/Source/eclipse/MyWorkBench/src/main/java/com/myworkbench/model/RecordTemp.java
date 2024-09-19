package com.myworkbench.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name="record_temp")
public class RecordTemp {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column
	private UUID uid;
	
	@Column
	private UUID taskUid;
	
	@Column
	private UUID processUid;
	
	@Column
	private Timestamp startTime;
	
	
}
