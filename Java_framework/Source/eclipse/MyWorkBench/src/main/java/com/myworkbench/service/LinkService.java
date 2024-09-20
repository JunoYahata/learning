package com.myworkbench.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myworkbench.model.Link;
import com.myworkbench.repository.LinkRepository;

@Service
public class LinkService {

	@Autowired
	LinkRepository repository;

	public List<Link> findAll() {
		return repository.findAll();
	}

	public Link findByUId(UUID uid) {
		return repository.findById(uid).orElseThrow();
	}

	public boolean insertOrUpdate(Link link) {
		return repository.save(link) != null;
	}

	public void delete(Link link) {
		repository.delete(link);
	}

}
