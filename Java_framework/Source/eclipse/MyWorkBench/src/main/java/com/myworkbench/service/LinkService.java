package com.myworkbench.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.repository.LinkRepository;

@Service
public class LinkService {
	
	@Autowired
	LinkRepository repository;

}
