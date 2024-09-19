package com.myworkbench.model;

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
@Table(name = "cd")
public class Cd {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column
	private UUID uid;

	@Column
	private String category;

	@Column
	private String name;

	@Column
	private String cd;

}
