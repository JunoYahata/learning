package com.myworkbench.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.repository.CdRepository;

@Service
public class CdService {
	
	@Autowired
	CdRepository repository;

}
