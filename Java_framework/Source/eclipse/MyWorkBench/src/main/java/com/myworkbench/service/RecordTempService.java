package com.myworkbench.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.model.RecordTemp;
import com.myworkbench.repository.RecordTempRepository;

@Service
public class RecordTempService {

	@Autowired
	RecordTempRepository repository;

	public RecordTemp find() {
		
		List<RecordTemp> recordTemp = repository.findAll();
		if(recordTemp != null && recordTemp.size()==1) {
			return recordTemp.get(0);
		}else {
			return null;
		}
	}
	
	public RecordTemp insert(RecordTemp recordTemp) {
		return repository.save(recordTemp);
	}
	
	public void deleteAll() {
		repository.deleteAll();
	}
	
}
