package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Process;
import com.myworkbench.repository.ProcessRepository;

@Service
public class ProcessService {

	@Autowired
	ProcessRepository repository;

	public List<Process> FindAll() {
		return repository.findAll();
	}
	
	public Process findByUId(UUID uid) {
		return repository.findById(uid).orElseThrow();
	}

	public List<Process> FindAllByPaUid(UUID uuid) {

		// 検索用エンティティ
		Process processProbe = new Process();
		processProbe.setPaUid(uuid);

		return repository.findAll(Example.of(processProbe));
	}
	
	public Process setStatusStart(UUID uid) {

		Process process = repository.findById(uid).orElseThrow();
		process.setProcessCd("1");
		return repository.save(process);
	}
	
	public Process setStatusStop(UUID uid) {

		Process process = repository.findById(uid).orElseThrow();
		process.setProcessCd("2");
		return repository.save(process);
	}
	
	public Process setStatusComplete(UUID uid) {

		Process process = repository.findById(uid).orElseThrow();
		process.setProcessCd("3");
		return repository.save(process);
	}
}
