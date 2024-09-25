package com.myworkbench.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Process;

public interface ProcessRepository extends JpaRepository<Process, UUID> {
	
	public List<Process> findAllByPaUidOrderByNumAscCreateTimeAsc(UUID paUid);

}
