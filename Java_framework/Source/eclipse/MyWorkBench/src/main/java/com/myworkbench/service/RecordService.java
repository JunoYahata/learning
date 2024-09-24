package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Record;
import com.myworkbench.repository.ProcessRepository;
import com.myworkbench.repository.RecordRepository;
import com.myworkbench.repository.TaskRepository;

@Service
public class RecordService {

	@Autowired
	RecordRepository recordRepository;

	@Autowired
	ProcessRepository processRepository;

	@Autowired
	TaskRepository taskRepository;

	public Record findByUId(UUID uid) {

		Record record = recordRepository.findById(uid).orElseThrow();
		record.setGpaUid(processRepository.findById(record.getPaUid()).orElseThrow().getPaUid());
		record.setTimesStr();
		return record;
	}

	public List<Record> findAllByPaUid(UUID uuid) {

		// 検索用エンティティ
		Record recordProbe = new Record();
		recordProbe.setPaUid(uuid);

		return recordRepository.findAll(Example.of(recordProbe));
	}

	public List<Record> findAll() {
		return recordRepository.findAll();
	}

	public boolean insertOrUpdate(Record record) {
		return recordRepository.save(record) != null;
	}

	public void delete(Record record) {
		recordRepository.delete(record);
	}

}
