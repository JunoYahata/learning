package com.myworkbench.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Record;

public interface RecordRepository extends JpaRepository<Record, UUID> {
	

	public List<Record> findAllByPaUidOrderByCreateTimeDesc(UUID paUid);

}
