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
    public NumRec find(int id) {
        return repository.findById(id).orElse(new NumRec());
    }
    
    public void save(NumRec numrec) {
    	repository.save(numrec);
    }
    
    public void countUpGestNum() {
        NumRec numRec = find(1);
        numRec.setNum(find(1).getNum()+1);
        save(numRec);
    }
    
}
