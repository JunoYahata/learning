package com.myworkbench.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Link;

public interface LinkRepository extends JpaRepository<Link, UUID> {
	
	

}
