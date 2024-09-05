package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.NumRec;
import com.example.repository.NumRecRepository;

@Service
public class NumRecService {

	@Autowired
    NumRecRepository repository;
	
    public List<NumRec> findAll() {
        return repository.findAll();
    }
    
}
