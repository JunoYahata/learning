package com.myworkbench.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Cd;
import com.myworkbench.repository.CdRepository;

@Service
public class CdService {

	@Autowired
	CdRepository repository;

	public List<Cd> findByCategory(String category) {

		Cd cd = new Cd();
		cd.setCategory(category);

		return repository.findAll((Example.of(cd)));

	}

}
