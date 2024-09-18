package com.myworkbench.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.repository.RecordTempRepository;

@Service
public class RecordTempService {
	
	@Autowired
	RecordTempRepository repository;

}
