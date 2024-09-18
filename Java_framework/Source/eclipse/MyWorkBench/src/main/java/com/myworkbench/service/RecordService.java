package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Record;
import com.myworkbench.repository.RecordRepository;

@Service
public class RecordService {
	
	@Autowired
	RecordRepository repository;
	
	public List<Record> FindAllByPaUid(UUID uuid){
		
		// 検索用エンティティ
		Record recordProbe = new Record();
		recordProbe.setPaUid(uuid);
		
		return  repository.findAll(Example.of(recordProbe));
	}

}
