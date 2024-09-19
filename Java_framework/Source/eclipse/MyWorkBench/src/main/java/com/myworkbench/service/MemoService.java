package com.myworkbench.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.repository.MemoRepository;

@Service
public class MemoService {

	@Autowired
	MemoRepository repository;

}
