package com.myworkbench.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.repository.SlackRepository;

@Service
public class SlackService {

	@Autowired
	SlackRepository repository;

}
