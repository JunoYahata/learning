package com.myworkbench.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myworkbench.model.Process;

public interface ProcessRepository extends JpaRepository<Process, Integer> {

}
